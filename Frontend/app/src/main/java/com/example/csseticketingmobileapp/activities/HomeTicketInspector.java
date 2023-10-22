package com.example.csseticketingmobileapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.csseticketingmobileapp.R;

public class HomeTicketInspector extends AppCompatActivity {

    Button scanQRBtn;
    private String ticketInspectorID; // Store the ticket Inspector ID
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_ticket_inspector);

        scanQRBtn = findViewById(R.id.idBTNScan);

        // Receive the ticket inspector ID from the intent
        Intent intent = getIntent();
        ticketInspectorID = intent.getStringExtra("userID");

        scanQRBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeTicketInspector.this, ScanQR.class);
                startActivity(i);
            }
        });
    }
}