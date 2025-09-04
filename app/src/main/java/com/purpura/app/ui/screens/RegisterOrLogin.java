package com.purpura.app.ui.screens;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseApp;
import com.purpura.app.configuration.Methods;
import com.purpura.app.R;

public class RegisterOrLogin extends AppCompatActivity {

    Methods methods = new Methods();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this); // inicializa Firebase
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_or_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button loginButton = findViewById(R.id.goToLoginButton);
        Button registerButton = findViewById(R.id.goToRegisterButton);

        loginButton.setOnClickListener(v ->
                methods.openScreen(this, Login.class)
        );

        registerButton.setOnClickListener(v ->
                methods.openScreen(this, Register.class)
        );

    }
}