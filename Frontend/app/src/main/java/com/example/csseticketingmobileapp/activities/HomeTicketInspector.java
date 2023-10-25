package com.example.csseticketingmobileapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.csseticketingmobileapp.R;
import com.example.csseticketingmobileapp.common.UserDataSingleton;

public class HomeTicketInspector extends AppCompatActivity {

    Button scanQRBtn, logoutBtn, reportViolationBtn;
    private String ticketInspectorID; // Store the ticket Inspector ID
    private TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_ticket_inspector);

        scanQRBtn = findViewById(R.id.idBTNScan);
        logoutBtn = findViewById(R.id.idBTNLogout);
        reportViolationBtn = findViewById(R.id.idBTNReportViolation);
        userName = findViewById(R.id.TVuserName);

        UserDataSingleton userDataSingleton = UserDataSingleton.getInstance();

        String email = userDataSingleton.getEmail();
        String role = userDataSingleton.getRole();
        String fullName = userDataSingleton.getFullName();
        ticketInspectorID = userDataSingleton.getUserId();

        userName.setText(fullName); // set username in home screen

        scanQRBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeTicketInspector.this, ScanQR.class);
                startActivity(i);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeTicketInspector.this, Login.class);
                startActivity(intent);
            }
        });

        reportViolationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeTicketInspector.this, ViolationReport.class);
                startActivity(intent);
            }
        });


    }
}