package com.example.csseticketingmobileapp.activities;

import static com.example.csseticketingmobileapp.config.ServerConfig.SERVER_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csseticketingmobileapp.R;
import com.example.csseticketingmobileapp.common.UserDataSingleton;

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

public class Login extends AppCompatActivity {

    EditText emailEditText, passwordEditText;
    Button signInButton;
    TextView signUpLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signInButton = findViewById(R.id.signInButton);
        signUpLink = findViewById(R.id.signUpLink);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Check if passenger exists in the database
                checkPassengerCredentials(email, password);
            }
        });

        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the registration page
                Intent intent = new Intent(Login.this, ChooseRole.class);
                startActivity(intent);
            }
        });
    }

    private void checkPassengerCredentials(String email, String password) {
        OkHttpClient client = new OkHttpClient();

        // Define the URL of your server's API endpoint for checking credentials
        String serverUrl = SERVER_URL + "/login/";
        System.out.println("------------ server's API endpoint for checking credentials" + SERVER_URL + "/login/ -------------");
        // Create a JSON request body with the email and password
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject requestBodyJson = new JSONObject();
        try {
            requestBodyJson.put("email", email);
            requestBodyJson.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(JSON, requestBodyJson.toString());

        // Create a POST request to check passenger credentials
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
                Log.e("MyTag", "Request failed: " + e.getMessage());
                System.out.println("------------------------------- Request failed: " + e.getMessage() + " -----------------------------------------");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseText = response.body().string();
                    Log.d("MyTag", "Response successful: " + responseText);
                    System.out.println("------------------------------- Response successful: " + responseText + "------------------------------------------");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonResponse = new JSONObject(responseText);
                                boolean isValid = jsonResponse.getBoolean("isValid");
//                                if (isValid) {
//                                    Toast.makeText(Login.this, "Login success", Toast.LENGTH_LONG).show();
//
//                                    boolean isPassenger = jsonResponse.getBoolean("isPassenger");
//                                    String userId;
//                                    Intent intent;
//
//                                    if(isPassenger){
//                                        // Credentials are valid, if user is a Passenger navigate to Passenger Home page with the passenger ID
//                                        userId = jsonResponse.getJSONObject("passenger").getString("_id");
//                                        System.out.println("######### Logged in Passenger ID : " + userId + " #########");
//                                        intent = new Intent(Login.this, HomePassenger.class);
//                                    }else{
//                                        // Credentials are valid, if user is a Ticket Inspector navigate to Ticket Inspector Home page with the ticket inspector ID
//                                        userId = jsonResponse.getJSONObject("ticketInspector").getString("_id");
//                                        System.out.println("######### Logged in Ticket Inspector ID : " + userId + " #########");
//                                        intent = new Intent(Login.this, HomeTicketInspector.class);
//                                    }
//                                    intent.putExtra("userID", userId);
//
//
//
//                                    startActivity(intent);
//                                }
                                if (isValid) {
                                    Toast.makeText(Login.this, "Login success", Toast.LENGTH_LONG).show();

                                    boolean isPassenger = jsonResponse.getBoolean("isPassenger");
                                    String userId, email, fName, lName, fullName, accountType, identifier, role;

                                    if (isPassenger) {
                                        userId = jsonResponse.getJSONObject("passenger").getString("_id");
                                        fName = jsonResponse.getJSONObject("passenger").getString("fName");
                                        lName = jsonResponse.getJSONObject("passenger").getString("lName");
                                        fullName = fName + " " + lName;
                                        email = jsonResponse.getJSONObject("passenger").getString("email");
                                        role = "Passenger";
                                    } else {
                                        userId = jsonResponse.getJSONObject("ticketInspector").getString("_id");
                                        email = jsonResponse.getJSONObject("ticketInspector").getString("email");
                                        fullName = jsonResponse.getJSONObject("ticketInspector").getString("fullName");
                                        role = "Ticket Inspector";
                                    }

                                    // Set user data in the Singleton
                                    UserDataSingleton userDataSingleton = UserDataSingleton.getInstance();
                                    userDataSingleton.setUserDataSingleton(userId, email, role, fullName);

                                    Intent intent;

                                    if (isPassenger) {
                                        intent = new Intent(Login.this, HomePassenger.class);
                                    } else {
                                        intent = new Intent(Login.this, HomeTicketInspector.class);
                                    }

                                    startActivity(intent);
                                }

                                else {
                                    // Invalid credentials, display an error message
                                    Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("MyTag", "JSON parsing error: " + e.getMessage());
                                System.out.println("------------------------------------- JSON parsing error: " + e.getMessage() + " --------------------------------------------------");
                            }
                        }
                    });
                } else {
                    // Handle an unsuccessful response
                    Log.e("MyTag", "Response not successful: " + response.message());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Invalid username or password",Toast.LENGTH_LONG).show();
                            System.out.println("------------------------------------- Server Error when checking credentials... ------------------------------------- ");
                        }
                    });
                }
            }
        });
    }

    public void openChooseRole(View view) {
        Intent intent = new Intent(Login.this, ChooseRole.class);
    }

}