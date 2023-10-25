package com.example.csseticketingmobileapp.activities;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csseticketingmobileapp.R;
import com.example.csseticketingmobileapp.common.SignUpFactory;
import com.example.csseticketingmobileapp.config.ServerConfig;
import com.example.csseticketingmobileapp.interfaces.Passenger;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.io.IOException;

public class RegisterPassenger extends AppCompatActivity {
    EditText editTextFirstName, editTextLastName, editTextEmail, editTextPassword;
    Spinner spinnerAccountType;
    Button btnRegister;
    TextView loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_passenger);

        // Initialize the form input fields
        editTextFirstName = findViewById(R.id.idEditTextfName);
        editTextLastName = findViewById(R.id.idEditTextlName);
        editTextEmail = findViewById(R.id.idEditTextEmail);
        editTextPassword = findViewById(R.id.idEditTextPassword);
        spinnerAccountType = findViewById(R.id.spinner);
        btnRegister = findViewById(R.id.idBTNSignUp);
        loginLink = findViewById(R.id.loginLink);


        // ------------------------------------set EditText Views based on spinner selected item ----------------------------------
        // Get references to the NIC and Passport EditText fields
        final EditText editTextNIC = findViewById(R.id.editTextNIC);
        final EditText editTextPassport = findViewById(R.id.editTextPassport);

        // Set the initial visibility of NIC and Passport EditText fields
        editTextNIC.setVisibility(View.VISIBLE);
        editTextPassport.setVisibility(View.GONE);

        // Set up the spinner with a list of account types
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.account_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAccountType.setAdapter(adapter);

        spinnerAccountType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedAccountType = spinnerAccountType.getSelectedItem().toString();

                // Show/hide the EditText fields based on the selected account type
                if (selectedAccountType.equals("Local")) {
                    editTextNIC.setVisibility(View.VISIBLE);
                    editTextPassport.setVisibility(View.GONE);
                } else if (selectedAccountType.equals("Foreign")) {
                    editTextNIC.setVisibility(View.GONE);
                    editTextPassport.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });
        // --------------------------------------------------------------------------------------------------------------------------

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the data from the form
                String fName = editTextFirstName.getText().toString();
                String lName = editTextLastName.getText().toString();
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                String accountType = spinnerAccountType.getSelectedItem().toString();

                // Determine whether the passenger is local or foreign
                String identifier = accountType.equalsIgnoreCase("local") ?
                       editTextNIC.getText().toString() : editTextPassport.getText().toString();

                // Create the passenger object based on account type
                Passenger passenger = SignUpFactory.createPassenger(RegisterPassenger.this, accountType, fName, lName, email, password, identifier);

                if (passenger != null) {
                    // Call the signUp() method on the passenger object
                    passenger.signUp();
                }
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the registration page
                Intent intent = new Intent(RegisterPassenger.this, Login.class);
                startActivity(intent);
            }
        });
    }
}
