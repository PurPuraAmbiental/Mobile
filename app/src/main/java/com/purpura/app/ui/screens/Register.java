package com.purpura.app.ui.screens;

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
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.purpura.app.R;
import com.purpura.app.configuration.Methods;
import com.purpura.app.model.EmpresaRequest;

public class Register extends AppCompatActivity {

    Methods methods = new Methods();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    GoogleSignInClient googleSignInClient;

    ActivityResultLauncher<Intent> googleLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    try {
                        GoogleSignInAccount account =
                                GoogleSignIn.getSignedInAccountFromIntent(result.getData())
                                        .getResult(ApiException.class);

                        if (account == null || account.getEmail() == null) {
                            Toast.makeText(this, "Erro ao obter conta do Google.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        String email = account.getEmail();

                        auth.fetchSignInMethodsForEmail(email).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                boolean existe = !task.getResult().getSignInMethods().isEmpty();

                                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                                auth.signInWithCredential(credential).addOnCompleteListener(loginTask -> {
                                    if (loginTask.isSuccessful()) {
                                        FirebaseUser user = auth.getCurrentUser();
                                        if (user != null) {
                                            verificarCNPJTelefone(user.getUid());
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
        setContentView(R.layout.activity_register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText edtEmail = findViewById(R.id.registerEmail);
        EditText edtTelefone = findViewById(R.id.registerPhone);
        EditText edtNome = findViewById(R.id.registerCompanyName);
        EditText edtCNPJ = findViewById(R.id.registerCNPJ);
        EditText edtSenha = findViewById(R.id.registerPassword);
        Button btnCadastrar = findViewById(R.id.registerButton);
        SignInButton btnGoogle = findViewById(R.id.loginWithGoogle);
        TextView txtLogin = findViewById(R.id.registerLoginText);

        // Cadastro manual
        btnCadastrar.setOnClickListener(v -> {
            String nome = edtNome.getText().toString().trim();
            String telefone = edtTelefone.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String cnpj = edtCNPJ.getText().toString().trim();
            String senha = edtSenha.getText().toString().trim();

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || telefone.isEmpty() || cnpj.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validações adicionais
            if (cnpj.length() != 14 || !cnpj.matches("\\d+")) {
                Toast.makeText(this, "CNPJ inválido. Deve ter 14 dígitos.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (telefone.length() < 10 || !telefone.matches("\\d+")) {
                Toast.makeText(this, "Telefone inválido. Deve ter no mínimo 10 dígitos.", Toast.LENGTH_SHORT).show();
                return;
            }

            auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = auth.getCurrentUser();
                    if (user != null) {

                        EmpresaRequest empresa = new EmpresaRequest(cnpj, email, "", nome, telefone);

                        // Salva no Firestore
                        FirebaseFirestore.getInstance()
                                .collection("empresa")
                                .document(user.getUid())
                                .set(empresa)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                                    methods.openScreen(this, MainActivity.class);
                                    finish();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(this, "Erro ao salvar no Firestore: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                });
                        // Atualiza nome no perfil
                        UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                                .setDisplayName(nome)
                                .build();
                        user.updateProfile(profile);


                    }
                } else {
                    Toast.makeText(this, "Erro ao cadastrar: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });

        // Configuração do Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("875459757357-fhss1jko4af6m0mj5jl9l65g2njufe0e.apps.googleusercontent.com")
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        btnGoogle.setOnClickListener(v -> {
            Intent intent = googleSignInClient.getSignInIntent();
            googleLauncher.launch(intent);
        });

        txtLogin.setOnClickListener(v -> {
            methods.openScreen(this, Login.class);
            finish();
        });

        FirebaseUser usuario = auth.getCurrentUser();
        if (usuario != null) {
            verificarCNPJTelefone(usuario.getUid());
        }
    }

    private void verificarCNPJTelefone(String uid) {
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
                            methods.openScreen(this, AddicionalInformacionsRegisterGoogle.class);
                            finish();
                        } else {
                            Toast.makeText(this, "Logado com sucesso", Toast.LENGTH_SHORT).show();
                            methods.openScreen(this, MainActivity.class);
                            finish();
                        }
                    } else {
                        Toast.makeText(this, "Complete seu cadastro", Toast.LENGTH_SHORT).show();
                        methods.openScreen(this, AddicionalInformacionsRegisterGoogle.class);
                        finish();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Erro ao verificar cadastro", Toast.LENGTH_SHORT).show();
                });
    }
}
