package com.example.csseticketingmobileapp.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.csseticketingmobileapp.R;
import com.example.csseticketingmobileapp.common.UserDataSingleton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class HomePassenger extends AppCompatActivity {
    private Button generateQRBtn, logoutBtn;
    private String passengerId; // Store passenger ID as a global variable
    private TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_passenger);

        generateQRBtn = findViewById(R.id.idBTNGenerate);
        logoutBtn = findViewById(R.id.idBTNLogout);
        userName = findViewById(R.id.TVuserName);

        UserDataSingleton userDataSingleton = UserDataSingleton.getInstance();

        String email = userDataSingleton.getEmail();
        String role = userDataSingleton.getRole();
        String fullName = userDataSingleton.getFullName();
        passengerId = userDataSingleton.getUserId();

        userName.setText(fullName);

        generateQRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateAndShowQRCode();
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePassenger.this, Login.class);
                startActivity(intent);
            }
        });
    }

    private void generateAndShowQRCode() {
        // Generate the QR code based on the passengerId
        String qrData = passengerId;
        try {
            Bitmap qrCodeBitmap = generateQRCode(qrData, 920); // Adjust the size of the QR code

            // Show the QR code in a dialog
            showQRCodeDialog(qrCodeBitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private Bitmap generateQRCode(String data, int size) throws WriterException {
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, size, size);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bitmap.setPixel(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }

        return bitmap;
    }

    private void showQRCodeDialog(Bitmap qrCodeBitmap) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Here is your QR code");

        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(qrCodeBitmap);

        dialogBuilder.setView(imageView);

        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog if needed
                dialog.dismiss();
            }
        });

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }
}
