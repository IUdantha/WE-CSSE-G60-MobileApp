package com.example.csseticketingmobileapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.csseticketingmobileapp.R;
import com.example.csseticketingmobileapp.config.ServerConfig;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ViolationReport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_violation_report);

        EditText editTextTime = findViewById(R.id.editTextTime);
        Button submitButton = findViewById(R.id.submitButton);
        EditText editTextName = findViewById(R.id.editTextName);
        EditText editTextIdentifier = findViewById(R.id.editTextIdentifier);
        EditText editTextBusNumber = findViewById(R.id.editTextBusNumber);
        EditText editTextDescription = findViewById(R.id.editTextDescription);

        editTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(ViolationReport.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // Handle the selected time (e.g., update the EditText)
                                editTextTime.setText(hourOfDay + ":" + minute);
                            }
                        },
                        hour, minute, true // true for 24-hour format, false for AM/PM format
                );

                timePickerDialog.show();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passengerName = editTextName.getText().toString();
                String identifier = editTextIdentifier.getText().toString();
                String busNumber = editTextBusNumber.getText().toString();
                String description = editTextDescription.getText().toString();
                String time = editTextTime.getText().toString();

                new SubmitViolationTask().execute(passengerName, identifier, busNumber, description, time);
            }
        });
    }

    private class SubmitViolationTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();
            String url = ServerConfig.SERVER_URL + "/passenger/violation";

            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            String jsonData = "{ \"passengerName\": \"" + params[0] + "\", \"identifier\": \"" + params[1] + "\", \"busNumber\": \"" + params[2] + "\", \"description\": \"" + params[3] + "\", \"time\": \"" + params[4] + "\" }";
            RequestBody requestBody = RequestBody.create(JSON, jsonData);

            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                return response.isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(ViolationReport.this, "Report Violation Success", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ViolationReport.this, "Report Violation Failed", Toast.LENGTH_LONG).show();
            }
            Intent intent = new Intent(ViolationReport.this, HomeTicketInspector.class);
            startActivity(intent);
        }
    }
}
