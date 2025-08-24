package com.purpura.app.ui.screens;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.purpura.app.R;
import com.purpura.app.configuration.Methods;
import com.purpura.app.ui.account.AccountFragment;

public class Login extends AppCompatActivity {

    Methods methods = new Methods();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView goToRegister = findViewById(R.id.loginRegisterText);
        Button loginButton = findViewById(R.id.loginButton);
        Button loginWithGoogle = findViewById(R.id.loginWithGoogle);

        goToRegister.setOnClickListener(v -> methods.openScreen(this, Register.class));
        loginButton.setOnClickListener(v -> methods.openScreen(this, MainActivity.class));

    }
}