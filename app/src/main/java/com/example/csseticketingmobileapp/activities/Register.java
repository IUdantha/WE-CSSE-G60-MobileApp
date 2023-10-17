package com.example.csseticketingmobileapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.csseticketingmobileapp.R;
import org.bson.Document;
import com.example.csseticketingmobileapp.network.MongoDBConnection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import android.util.Log;


public class Register extends AppCompatActivity {

    MongoDatabase database = MongoDBConnection.getDatabase();
    MongoCollection<Document> collection = database.getCollection("passenger");


    private Spinner accountTypeSpinner;
    private EditText nicEditText;
    private EditText passportEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button registerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        accountTypeSpinner = findViewById(R.id.spinnerAccountType);
        nicEditText = findViewById(R.id.editTextNIC);
        passportEditText = findViewById(R.id.editTextPassport);
        firstNameEditText = findViewById(R.id.editTextFirstName);
        lastNameEditText = findViewById(R.id.editTextLastName);
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        registerButton = findViewById(R.id.idBTNRegister);

        Log.d("Register","Inside On create method");

        // Set up the spinner with an OnItemSelectedListener
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.account_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountTypeSpinner.setAdapter(adapter);
        accountTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Check the selected item and toggle visibility of NIC or Passport EditText
                String selectedAccountType = accountTypeSpinner.getSelectedItem().toString();
                if ("Local".equals(selectedAccountType)) {
                    nicEditText.setVisibility(View.VISIBLE);
                    passportEditText.setVisibility(View.GONE);
                } else {
                    nicEditText.setVisibility(View.GONE);
                    passportEditText.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve data from EditText fields
                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                boolean dataIsValid = true;

                if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    dataIsValid = false;
                }

                if (dataIsValid) {
                    // Create a new Document for the passenger
                    Document passengerDocument = new Document("fName", firstName)
                            .append("lName", lastName)
                            .append("email", email)
                            .append("password", password);

                    // Insert the passenger document into the "passengers" collection
                    collection.insertOne(passengerDocument);

                    // Provide feedback to the user
                    showToast("Registration successful");

                    // Redirect to another activity or perform other actions as needed.
                } else {
                    // Data validation failed
                    showToast("Registration unsuccessful. Please check your input.");
                }
            }
        }
        );

    }

    // Helper method to display a Toast message
    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private boolean isValidRegistrationData(String firstName, String lastName, String email, String password) {
        if (firstName.isEmpty()) {
            // First name is empty
            firstNameEditText.setError("First name is required.");
            firstNameEditText.requestFocus();
            return false;
        }

        if (lastName.isEmpty()) {
            // Last name is empty
            lastNameEditText.setError("Last name is required.");
            lastNameEditText.requestFocus();
            return false;
        }

        if (email.isEmpty()) {
            // Email is empty
            emailEditText.setError("Email is required.");
            emailEditText.requestFocus();
            return false;
        } else if (!isValidEmail(email)) {
            // Email is not in a valid format
            emailEditText.setError("Invalid email format.");
            emailEditText.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            // Password is empty
            passwordEditText.setError("Password is required.");
            passwordEditText.requestFocus();
            return false;
        }

        // If all checks pass, the data is considered valid
        return true;
    }

    private boolean isValidEmail(String email) {
        // This is a basic email validation using a simple regular expression
        // You can use a more complex regular expression or a library like Android's Patterns.EMAIL_ADDRESS for better validation
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    public void openSignInScreen(View view) {
        Intent intent = new Intent(this, Register.class); // Replace Register.class with the actual Register activity class name
        startActivity(intent);
    }


}
