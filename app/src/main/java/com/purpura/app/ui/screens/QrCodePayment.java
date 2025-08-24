package com.purpura.app.ui.screens;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.purpura.app.R;
import com.purpura.app.configuration.Methods;

public class QrCodePayment extends AppCompatActivity {

    Methods methods = new Methods();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_qr_code_payment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView backButton = ((ImageView) findViewById(R.id.qrCodePaymentBackButton));
        Button continueButton = ((Button) findViewById(R.id.qrCodePaymentContinueButton));

        backButton.setOnClickListener(v -> methods.openScreen(this, ChoosePaymentMethod.class));
        continueButton.setOnClickListener(v -> {methods.openScreen(this, PaymetStatus.class);});

    }
}