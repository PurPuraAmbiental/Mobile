package com.purpura.app.ui.screens;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import com.google.android.gms.common.SignInButton;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.purpura.app.R;
import com.purpura.app.configuration.Methods;
import com.purpura.app.ui.account.AccountFragment;

public class Login extends AppCompatActivity {

    Methods methods = new Methods();
    FirebaseAuth objAutenticar = FirebaseAuth.getInstance();
    GoogleSignInClient googleSignInClient;

    ActivityResultLauncher<Intent> telaGoogle = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    try {
                        GoogleSignInAccount signInAccount =
                                GoogleSignIn.getSignedInAccountFromIntent(result.getData())
                                        .getResult(ApiException.class);

                        if (signInAccount == null || signInAccount.getEmail() == null) {
                            Toast.makeText(this, "Erro ao obter conta do Google.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        String email = signInAccount.getEmail();

                        objAutenticar.fetchSignInMethodsForEmail(email)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        boolean existe = !task.getResult().getSignInMethods().isEmpty();

                                        AuthCredential authCredential =
                                                GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);

                                        objAutenticar.signInWithCredential(authCredential)
                                                .addOnCompleteListener(loginTask -> {
                                                    if (loginTask.isSuccessful()) {
                                                        FirebaseUser user = objAutenticar.getCurrentUser();
                                                        if (user != null) {
                                                            verificarCNPJTelefone(user.getUid(), existe);
                                                        }
                                                    } else {
                                                        Toast.makeText(this, "Erro ao logar com Google.", Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                    } else {
                                        Toast.makeText(this, "Erro ao verificar email: " + task.getException(), Toast.LENGTH_LONG).show();
                                    }
                                });

                    } catch (ApiException e) {
                        Toast.makeText(this, "Erro Google Sign-In: " + e.getMessage(), Toast.LENGTH_LONG).show();
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

        TextView goToRegister = findViewById(R.id.loginRegisterText);
        Button loginButton = findViewById(R.id.loginButton);
        EditText edtEmail = findViewById(R.id.loginEmail);
        EditText edtSenha = findViewById(R.id.loginPassword);
        SignInButton btnGoogle = findViewById(R.id.loginWithGoogle);

        goToRegister.setOnClickListener(v -> methods.openScreenActivity(this, Register.class));

        loginButton.setOnClickListener(v -> {
            String txtEmail = edtEmail.getText().toString().trim();
            String txtSenha = edtSenha.getText().toString().trim();

            if (txtEmail.isEmpty() || txtSenha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            objAutenticar.signInWithEmailAndPassword(txtEmail, txtSenha)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = objAutenticar.getCurrentUser();
                            if (user != null) {
                                verificarCNPJTelefone(user.getUid(), true);
                            }
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

        btnGoogle.setOnClickListener(v -> {
            Intent intent = googleSignInClient.getSignInIntent();
            telaGoogle.launch(intent);
        });

        FirebaseUser usuario = objAutenticar.getCurrentUser();
        if (usuario != null) {
            verificarCNPJTelefone(usuario.getUid(), true);
        }
    }

    private void verificarCNPJTelefone(String uid, boolean jaLogado) {
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
                            if (jaLogado) {
                                Toast.makeText(this, "Logado com sucesso", Toast.LENGTH_SHORT).show();
                            }
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
                    Toast.makeText(this, "Erro ao verificar cadastro", Toast.LENGTH_SHORT).show();
                });
    }

}