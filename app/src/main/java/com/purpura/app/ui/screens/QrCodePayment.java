package com.purpura.app.ui.screens;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.purpura.app.R;
import com.purpura.app.configuration.Methods;
import com.purpura.app.ui.screens.errors.InternetError;

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

        ImageView backButton = findViewById(R.id.qrCodePaymentBackButton);
        Button continueButton = findViewById(R.id.qrCodePaymentContinueButton);
        ImageView copyButton = findViewById(R.id.qrCodePaymentCopyPasteButton);
        TextView qrCodeTextView = findViewById(R.id.qrCodePaymentPixURL);

        //----- SetOnClickListener -----//

        backButton.setOnClickListener(v -> finish());

        continueButton.setOnClickListener(v -> methods.openScreenActivity(this, PaymentStatus.class));

        copyButton.setOnClickListener(v -> {
            String qrCodeText = qrCodeTextView.getText().toString();
            methods.copyText(this, qrCodeText);
        });
    }
}
