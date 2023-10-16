package com.example.csseticketingmobileapp.activities;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.csseticketingmobileapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class GenerateQR extends AppCompatActivity {
    private ImageView qrCodeIV;
    private TextInputEditText dataEdit;
    private Button generateQRBtn, backBtn;
    private TextView instructionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr);

        qrCodeIV = findViewById(R.id.idIVQRCode);
        dataEdit = findViewById(R.id.idEditData);
        generateQRBtn = findViewById(R.id.idBTNGenerateSub);
        instructionText = findViewById(R.id.idTVsubHead);
        backBtn = findViewById(R.id.idBTNBack);

        generateQRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = dataEdit.getText().toString();
                if (data.isEmpty()) {
                    Toast.makeText(GenerateQR.this, "Please enter some data to generate QR code", Toast.LENGTH_LONG).show();
                } else {
                    // Generate the QR code using ZXing
                    try {
                        Bitmap qrCodeBitmap = generateQRCode(data, 920); // Adjust the size of the QR
                        instructionText.setVisibility(View.GONE);
                        qrCodeIV.setImageBitmap(qrCodeBitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GenerateQR.this, HomePassenger.class);
                startActivity(i);
            }
        });
    }

    // Utility function to generate a QR code using ZXing
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
}
