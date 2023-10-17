package com.example.csseticketingmobileapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.csseticketingmobileapp.R;

public class Home extends AppCompatActivity {

    private Button generateQRBtn, scanQRBtn, loginBtn, registerBtn, violationBtn, myAccountBtn, homePassengerBtn, homeInspectorBtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        generateQRBtn = findViewById(R.id.idBTNGenerate);
        scanQRBtn = findViewById(R.id.idBTNScan);
        homePassengerBtn = findViewById(R.id.idBTNHomePassenger);
        homeInspectorBtn = findViewById(R.id.idBTNHomeInspector);
        loginBtn = findViewById(R.id.idBTNLogin);
        registerBtn = findViewById(R.id.idBTNRegister);
        myAccountBtn = findViewById(R.id.idBTNMyAccount);
        violationBtn = findViewById(R.id.idBTNViolation);

        generateQRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, GenerateQR.class);
                startActivity(i);
            }
        });

        scanQRBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, ScanQR.class);
                startActivity(i);
            }
        });

        homePassengerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, HomePassenger.class);
                startActivity(i);
            }
        });

        homeInspectorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, HomeTicketInspector.class);
                startActivity(i);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, Login.class);
                startActivity(i);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, Register.class);
                startActivity(i);
            }
        });

        myAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, MyAccount.class);
                startActivity(i);
            }
        });

        violationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, ViolationForm.class);
                startActivity(i);
            }
        });
    }
}