package com.example.csseticketingmobileapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.csseticketingmobileapp.R;

public class Violation extends AppCompatActivity {
    Button buttonSubmit = findViewById(R.id.buttonClear);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_violation);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Violation.this, HomeTicketInspector.class);
                startActivity(i);
            }
        });
    }
}


