package com.purpura.app.ui.screens;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;

import com.purpura.app.R;
import com.purpura.app.configuration.Methods;
import com.purpura.app.ui.screens.autentication.RegisterOrLogin;


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
