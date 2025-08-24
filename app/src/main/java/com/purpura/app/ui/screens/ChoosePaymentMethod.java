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

public class ChoosePaymentMethod extends AppCompatActivity {

    Methods methods = new Methods();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choose_payment_method);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView backButton = ((ImageView) findViewById(R.id.choosePaymentMethodBackButton));
        Button continueBuying = ((Button) findViewById(R.id.choosePaymentMethodContinueBuyingButton));

        backButton.setOnClickListener(v -> methods.openScreen(this, MainActivity.class));
        continueBuying.setOnClickListener(v -> methods.openScreen(this, QrCodePayment.class));

    }
}