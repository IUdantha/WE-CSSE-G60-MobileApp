package com.example.csseticketingmobileapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.csseticketingmobileapp.R;

public class HomePassenger extends AppCompatActivity {

    private Button generateQRBtn, scanQRBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_passenger);

        generateQRBtn = findViewById(R.id.idBTNGenerate);
        scanQRBtn = findViewById(R.id.idBTNScan);

        generateQRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePassenger.this, GenerateQR.class);
                startActivity(i);
            }
        });

//        scanQRBtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(HomePassenger.this, ScanQR.class);
//                startActivity(i);
//            }
//        });
    }
}