package com.example.csseticketingmobileapp.network;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csseticketingmobileapp.R;
import com.example.csseticketingmobileapp.activities.ScanQR;
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

                                        ImageView imageViewVerify = context.findViewById(R.id.imageViewVerify);
                                        TextView passengerInfoTextView = context.findViewById(R.id.passengerInfoTextView);
                                        ImageView backIcon = context.findViewById(R.id.backIcon);
                                        Button btnScanAgain = context.findViewById(R.id.btnScanAgain);

                                        imageViewVerify.setVisibility(View.VISIBLE);
                                        backIcon.setVisibility(View.VISIBLE);
                                        btnScanAgain.setVisibility(View.VISIBLE);
                                        passengerInfoTextView.setVisibility(View.VISIBLE);

                                        passengerInfoTextView.setText(passengerInfo);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    // It's a violation, prompt the inspector to report it
                                    // context.showViolationReportForm(passengerID);
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
