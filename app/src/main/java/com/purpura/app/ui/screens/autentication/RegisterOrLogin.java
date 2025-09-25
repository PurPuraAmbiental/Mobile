package com.purpura.app.ui.screens.autentication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.purpura.app.R;
import com.purpura.app.configuration.Methods;
import com.purpura.app.ui.screens.MainActivity;

public class RegisterOrLogin extends AppCompatActivity {

    Methods methods = new Methods();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_or_login);

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {}
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button loginButton = findViewById(R.id.goToLoginButton);
        Button registerButton = findViewById(R.id.goToRegisterButton);

        loginButton.setOnClickListener(v -> methods.openScreenActivity(this, Login.class));

        registerButton.setOnClickListener(v -> methods.openScreenActivity(this, Register.class));
        registerButton.setOnClickListener(v ->
                methods.openScreenActivity(this, Register.class)
        );

        // Verifica usuário logado
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        if (usuario != null) {
            validarCadastro(usuario.getUid());
        }
    }

    private void validarCadastro(String uid) {
        FirebaseFirestore.getInstance()
                .collection("empresa")
                .document(uid)
                .get()
                .addOnSuccessListener(document -> {
                    if (document.exists()) {
                        String cnpj = document.getString("cnpj");
                        String telefone = document.getString("telefone");

                        if (cnpj == null || cnpj.isEmpty() || telefone == null || telefone.isEmpty()) {
                            Toast.makeText(this, "Complete seu cadastro", Toast.LENGTH_SHORT).show();
                            methods.openScreenActivity(this, AddicionalInformacionsRegisterGoogle.class);
                            finish();
                        } else {
                            Toast.makeText(this, "Logado com sucesso", Toast.LENGTH_SHORT).show();
                            methods.openScreenActivity(this, MainActivity.class);
                            finish();
                        }
                    } else {
                        Toast.makeText(this, "Complete seu cadastro", Toast.LENGTH_SHORT).show();
                        methods.openScreenActivity(this, AddicionalInformacionsRegisterGoogle.class);
                        finish();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FirestoreDebug", "Erro ao buscar dados da empresa", e);
                    Toast.makeText(this, "Erro ao verificar cadastro", Toast.LENGTH_SHORT).show();
                });
    }
}