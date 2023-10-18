package com.example.csseticketingmobileapp.activities;

import android.content.Intent;
import android.health.connect.datatypes.units.Length;
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
                   Toast.makeText(Register.this, "Data valid", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Register.this, "Data invalid", Toast.LENGTH_SHORT).show();
                }
            }
        }
        );

    }

    public void openSignInScreen(View view) {
        Intent intent = new Intent(this, Register.class); // Replace Register.class with the actual Register activity class name
        startActivity(intent);
    }


}
