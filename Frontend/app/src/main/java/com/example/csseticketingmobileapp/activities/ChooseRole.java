package com.example.csseticketingmobileapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.csseticketingmobileapp.R;

public class ChooseRole extends AppCompatActivity {

    private RadioGroup accountTypeGroup;
    private RadioButton passengerOption;
    private RadioButton inspectorOption;
    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_role);

        accountTypeGroup = findViewById(R.id.account_type_group);
        passengerOption = findViewById(R.id.passenger_option);
        inspectorOption = findViewById(R.id.inspector_option);
        continueButton = findViewById(R.id.continue_button);

        // Initially, disable the "Continue" button
        continueButton.setEnabled(false);

        // Add a RadioGroup.OnCheckedChangeListener to enable the button when a radio button is selected
        accountTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == passengerOption.getId() || checkedId == inspectorOption.getId()) {
                    // Enable the "Continue" button when a radio button is selected
                    continueButton.setEnabled(true);
                } else {
                    // Disable the "Continue" button if no radio button is selected
                    continueButton.setEnabled(false);
                }
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = accountTypeGroup.getCheckedRadioButtonId();

                try {
                    if (selectedId == passengerOption.getId()) {
                        Intent passengerIntent = new Intent(ChooseRole.this, RegisterPassenger.class);
                        startActivity(passengerIntent);
                    }else if (selectedId == inspectorOption.getId()) {
                        Intent inspectorIntent = new Intent(ChooseRole.this, RegisterTicketInspector.class);
                        startActivity(inspectorIntent);
                    }
                } catch (Exception e) {
                    Log.e("----------------------ChooseRole", "Error: " + e.getMessage() + "-------------");
                }
            }
        });
    }
}
