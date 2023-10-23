package com.example.csseticketingmobileapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.csseticketingmobileapp.R;
import com.example.csseticketingmobileapp.config.ServerConfig;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class RegisterTicketInspector extends AppCompatActivity {
    EditText editTextEmpID, editTextfullName, editTextEmail, editTextPassword;
    Button btnRegister, btnOtherHome;
    TextView loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_ticket_inspector);

        // Initialize the form input fields
        editTextEmpID = findViewById(R.id.idEditTextEmpID);
        editTextfullName = findViewById(R.id.idEditTextInspectorName);
        editTextEmail = findViewById(R.id.idEditTextInspectorEmail);
        editTextPassword = findViewById(R.id.idEditTextInspectorPassword);
        btnRegister = findViewById(R.id.idBTNSignUp);
//        loginLink = findViewById(R.id.loginLink);
//        btnOtherHome = findViewById(R.id.idBTNOtherHome);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the data from the form
                String empID = editTextEmpID.getText().toString();
                String fullName = editTextfullName.getText().toString();
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                // Call the method to add a Ticket Inspector
                addTicketInspector(empID, fullName, email, password);
            }
        });

//        btnOtherHome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(RegisterTicketInspector.this, Home.class);
//                startActivity(i);
//            }
//        });
    }

    private void addTicketInspector( String empID, String fullName, String email, String password) {
        OkHttpClient client = new OkHttpClient();

        // Define the URL of your server's API endpoint
        String serverUrl = ServerConfig.SERVER_URL + "/ticketInspector/add";

        // Create a JSON request body with the passenger data
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String json = "{\"empID\":\"" + empID + "\",\"fullName\":\"" + fullName + "\",\"email\":\"" + email + "\",\"password\":\"" + password + "\"}";
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
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // Handle a successful response from the server
                    String responseText = response.body().string();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), responseText, Toast.LENGTH_SHORT).show();
                        }
                    });

                    Intent i = new Intent(RegisterTicketInspector.this, Login.class);
                    startActivity(i);

                } else {
                    // Handle an unsuccessful response
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Your username or password is incorrect...", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}