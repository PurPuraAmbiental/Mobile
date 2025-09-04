package com.purpura.app.ui.screens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.purpura.app.R;
import com.purpura.app.configuration.Methods;
import com.purpura.app.model.EmpresaRequest;

public class Login extends AppCompatActivity {

    Methods methods = new Methods();
    FirebaseAuth objAutenticar = FirebaseAuth.getInstance();
    GoogleSignInClient googleSignInClient;

    // Launcher para login com Google
    ActivityResultLauncher<Intent> telaGoogle = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    try {
                        GoogleSignInAccount signInAccount =
                                GoogleSignIn.getSignedInAccountFromIntent(result.getData())
                                        .getResult(ApiException.class);

                        if (signInAccount == null) {
                            Toast.makeText(this, "Erro ao obter conta do Google.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        String email = signInAccount.getEmail();
                        if (email == null) {
                            Toast.makeText(this, "Email não encontrado.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        objAutenticar.fetchSignInMethodsForEmail(email)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        boolean existe = !task.getResult().getSignInMethods().isEmpty();

                                        // Cria credencial do Google
                                        AuthCredential authCredential =
                                                GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);

                                        // Tenta logar no Firebase
                                        objAutenticar.signInWithCredential(authCredential)
                                                .addOnCompleteListener(Login.this, loginTask -> {
                                                    if (loginTask.isSuccessful()) {
                                                        if (existe) {
                                                            Toast.makeText(Login.this, "Logado com sucesso", Toast.LENGTH_LONG).show();
                                                            methods.openScreen(this, MainActivity.class);
                                                            finish();
                                                        } else {
                                                            // Novo usuário -> vai para tela de cadastro extra
                                                            Toast.makeText(Login.this, "Novo usuário, complete o cadastro.", Toast.LENGTH_LONG).show();
                                                            methods.openScreen(this, AddicionalInformacionsRegisterGoogle.class);
                                                            finish();
                                                        }
                                                    } else {
                                                        Toast.makeText(Login.this, "Erro ao logar com Google.", Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                    } else {
                                        Toast.makeText(Login.this, "Erro ao verificar email: " + task.getException(), Toast.LENGTH_LONG).show();
                                    }
                                });

                    } catch (ApiException e) {
                        Toast.makeText(Login.this, "Erro Google Sign-In: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });

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

        EmpresaRequest usuarioEmpresa;
        TextView goToRegister = findViewById(R.id.loginRegisterText);
        Button loginButton = findViewById(R.id.loginButton);

        goToRegister.setOnClickListener(v -> methods.openScreen(this, Register.class));

        loginButton.setOnClickListener(v -> {
            String txtEmail = ((EditText) findViewById(R.id.loginEmail)).getText().toString();
            String txtSenha = ((EditText) findViewById(R.id.loginPassword)).getText().toString();

            objAutenticar.signInWithEmailAndPassword(txtEmail, txtSenha)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            methods.openScreen(this, MainActivity.class);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Erro ao Logar", Toast.LENGTH_LONG).show();
                        }
                    });
        });

        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("875459757357-fhss1jko4af6m0mj5jl9l65g2njufe0e.apps.googleusercontent.com")
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, options);

        ((SignInButton) findViewById(R.id.registerWithGoogle)).setOnClickListener(v -> {
            Intent intent = googleSignInClient.getSignInIntent();
            telaGoogle.launch(intent);
        });

        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        if (usuario != null) {
            Toast.makeText(this, "Logado com sucesso", Toast.LENGTH_SHORT).show();
            methods.openScreen(this, MainActivity.class);
            finish();
        }
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        googleSignInClient.signOut();
        finish();
    }
}
