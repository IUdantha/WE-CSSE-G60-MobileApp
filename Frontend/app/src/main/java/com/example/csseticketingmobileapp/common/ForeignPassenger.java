package com.example.csseticketingmobileapp.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.csseticketingmobileapp.activities.Home;
import com.example.csseticketingmobileapp.activities.HomePassenger;
import com.example.csseticketingmobileapp.activities.Login;
import com.example.csseticketingmobileapp.config.ServerConfig;
import com.example.csseticketingmobileapp.interfaces.Passenger;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ForeignPassenger implements Passenger {
    private String fName;
    private String lName;
    private String email;
    private String password;
    private String passport;
    private String accountType;
    private Context context; // Add context for displaying Toast and starting activities

    public ForeignPassenger(Context context, String fName, String lName, String email, String password, String passport, String accountType) {
        this.context = context;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.password = password;
        this.passport = passport;
        this.accountType = accountType;
    }

    @Override
    public void signUp() {
        OkHttpClient client = new OkHttpClient();

        // Define the URL of your server's API endpoint
        String serverUrl = ServerConfig.SERVER_URL + "/passenger/add";

        // Create a JSON request body with the passenger data
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String json = "{\"fName\":\"" + fName + "\",\"lName\":\"" + lName + "\",\"email\":\"" + email + "\",\"password\":\"" + password + "\",\"accountType\":\"" + accountType + "\", \"identifier\":\"" + passport + "\"}";
        RequestBody requestBody = RequestBody.create(JSON, json);

        // Create a POST request
        Request request = new Request.Builder()
                .url(serverUrl)
                .post(requestBody)
                .build();

        // Send the request asynchronously
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle request failure (e.g., network issues)
                e.printStackTrace();

                // Display an error toast
                showToast("Network request failed.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // Handle a successful response from the server
                    String responseText = response.body().string();

                    // Display a success toast
                    showToast("Foreign Passenger Account Successfully created...");

                    // Redirect to the Home activity
                    navigateToHomeActivity();
                } else {
                    // Handle an unsuccessful response
                    // Display an error toast
                    showToast("Server Error when passing data.");
                }
            }
        });
    }

    // Helper method to show a toast message on the main UI thread
    private void showToast(final String message) {
        // Use the context to display the toast
        if (context != null) {
            ((Activity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    // Helper method to navigate to the Home activity
    private void navigateToHomeActivity() {
        if (context != null) {
            Intent intent = new Intent(context, Login.class);
            context.startActivity(intent);
        }
    }
}