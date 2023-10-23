package com.example.csseticketingmobileapp.network;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.csseticketingmobileapp.R;
import com.example.csseticketingmobileapp.activities.HomePassenger;
import com.example.csseticketingmobileapp.activities.HomeTicketInspector;
import com.example.csseticketingmobileapp.activities.ScanQR;
import com.example.csseticketingmobileapp.activities.ViolationReport;
import com.example.csseticketingmobileapp.config.ServerConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PassengerViolationHandler {

    public static void verifyPassengerOrReportViolation(String passengerID, ScanQR context) {
        OkHttpClient client = new OkHttpClient();
        String serverUrl = ServerConfig.SERVER_URL + "/passenger/checkViolation";

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject requestBodyJson = new JSONObject();

        try {
            requestBodyJson.put("passengerID", passengerID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody requestBody = RequestBody.create(JSON, requestBodyJson.toString());

        Request request = new Request.Builder()
                .url(serverUrl)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                new Handler(Looper.getMainLooper()).post(() -> {
                    Toast.makeText(context, "Failed to connect to the server", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (Response responseCopy = response) { // Use try-with-resources to close the response
                    if (responseCopy.isSuccessful()) {
                        String responseText = responseCopy.body().string();
                        try {
                            JSONObject jsonResponse = new JSONObject(responseText);
                            boolean isValid = jsonResponse.getBoolean("isValid");

                            new Handler(Looper.getMainLooper()).post(() -> {
                                if (isValid) {
                                    try {
                                        // Parse passenger details from the JSON response
                                        JSONObject passengerData = jsonResponse.getJSONObject("passenger");

                                        // Extract passenger information
                                        String passengerId = passengerData.getString("_id");
                                        String firstName = passengerData.getString("fName");
                                        String lastName = passengerData.getString("lName");
                                        String email = passengerData.getString("email");
                                        String identifier = passengerData.getString("identifier");
                                        String accountType = passengerData.getString("accountType");

                                        String passengerInfo = "Passenger ID: " + passengerId + "\n\n" +
                                                "Name: " + firstName + " " + lastName + "\n\n" +
                                                "Email: " + email + "\n\n" +
                                                "Account Type: " + accountType + "\n\n";

                                        if(accountType.toString().equals("Local")){
                                            String nic = identifier;
                                            passengerInfo = passengerInfo + "NIC: " + nic;
                                        }
                                        else {
                                            String passport = identifier;
                                            passengerInfo = passengerInfo + "Passport: " + passport;
                                        }

                                        // Create a custom dialog to display passenger information
                                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                                        View dialogView = context.getLayoutInflater().inflate(R.layout.activity_scan_qr, null);
                                        dialogBuilder.setView(dialogView);

                                        // Find dialog elements and set the passenger info
                                        ImageView dialogImageViewVerify = dialogView.findViewById(R.id.imageViewVerify);
                                        Button dialogBtnScanAgain = dialogView.findViewById(R.id.btnScanAgain);
                                        Button dialogBtnOk = dialogView.findViewById(R.id.btnOk);
                                        TextView dialogHeader= dialogView.findViewById(R.id.TVdialogHeader);
                                        TextView dialogPassengerInfoTextView = dialogView.findViewById(R.id.passengerInfoTextView);

                                        // Set visibility for dialog elements
                                        dialogImageViewVerify.setVisibility(View.VISIBLE);
                                        dialogBtnScanAgain.setVisibility(View.VISIBLE);
                                        dialogBtnOk.setVisibility(View.VISIBLE);
                                        dialogHeader.setVisibility(View.VISIBLE);
                                        dialogPassengerInfoTextView.setVisibility(View.VISIBLE);

                                        // Set passenger info
                                        dialogPassengerInfoTextView.setText(passengerInfo);

                                        AlertDialog dialog = dialogBuilder.create();
                                        dialog.show();

                                        dialogBtnScanAgain.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss(); // Dismiss the dialog
                                                Intent intent = new Intent(context, ScanQR.class);
                                                context.startActivity(intent);
                                                context.finish(); // Close the current ScanQR activity
                                            }
                                        });

                                        dialogBtnOk.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss(); // Dismiss the dialog
                                                Intent intent = new Intent(context, HomeTicketInspector.class);
                                                context.startActivity(intent);
                                                context.finish(); // Optionally, close the current ScanQR activity
                                            }
                                        });

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    // Create a custom dialog to display passenger information
                                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                                    View dialogView = context.getLayoutInflater().inflate(R.layout.dialog_detect_violation, null);
                                    dialogBuilder.setView(dialogView);

                                    Button dialogBtnReport= dialogView.findViewById(R.id.btnReport);
                                    Button dialogBtnScanAgain = dialogView.findViewById(R.id.btnScanAgain);

                                    AlertDialog dialog = dialogBuilder.create();
                                    dialog.show();

                                    dialogBtnReport.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss(); // Dismiss the dialog
                                            Intent intent = new Intent(context, ViolationReport.class);
                                            context.startActivity(intent);
                                            context.finish(); // Optionally, close the current ScanQR activity
                                        }
                                    });

                                    dialogBtnScanAgain.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss(); // Dismiss the dialog
                                            Intent intent = new Intent(context, ScanQR.class);
                                            context.startActivity(intent);
                                            context.finish(); // Close the current ScanQR activity
                                        }
                                    });
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        new Handler(Looper.getMainLooper()).post(() -> {
                            Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
                        });
                    }
                }
            }
        });
    }
}
