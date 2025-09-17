package com.purpura.app.ui.screens;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Handler;

import com.purpura.app.R;
import com.purpura.app.configuration.Methods;


public class SplashScreen extends AppCompatActivity {
    Methods methods = new Methods();
    private void abrirTela() {
        methods.openScreenActivity(this, RegisterOrLogin.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(this::abrirTela, 4000);
    }


}
