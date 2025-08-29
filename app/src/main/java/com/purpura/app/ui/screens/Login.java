package com.purpura.app.ui.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.purpura.app.R;
import com.purpura.app.configuration.Methods;
import com.purpura.app.model.EmpresaRequest;
import com.purpura.app.ui.account.AccountFragment;

public class Login extends AppCompatActivity {

    Methods methods = new Methods();
    FirebaseAuth objAutenticar = FirebaseAuth.getInstance();
    GoogleSignInClient googleSignInClient;
    ActivityResultLauncher<Intent> telaGoogle = registerForActivityResult(new ActivityResultContracts
                    .StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    // Conseguiu abrir a tela?
                    if (result.getResultCode() == RESULT_OK) {
                        Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                        GoogleSignInAccount signInAccount = accountTask.getResult();
                        AuthCredential authCredential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(), null);

                        FirebaseAuth.getInstance().signInWithCredential(authCredential)
                                .addOnCompleteListener(Login.this, task -> {
                                    if (task.isSuccessful()) {
                                       Toast.makeText(Login.this, "Logado com sucesso",
                                             Toast.LENGTH_LONG).show();
                                    } else {
                                          Toast.makeText(Login.this, "Erro ao Logar", Toast.LENGTH_LONG).show();
                                      }
                                    }
                                );
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

        // Ir para Cadastro
        goToRegister.setOnClickListener(v -> methods.openScreen(this, Register.class));

        // Login Email e Senha
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Colocar os Controles para não enviar Dados Vazios
                // Vc faz isso no Cadastro
                String txtEmail = ((EditText) findViewById(R.id.loginEmail)).getText().toString();
                String txtSenha = ((EditText) findViewById(R.id.loginPassword)).toString().toString();
                objAutenticar.signInWithEmailAndPassword(txtEmail, txtSenha).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(com.google.android.gms.tasks.Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Abrir Tela Principal
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                }else{
                                    // Mensagem de Erro
                                    Toast.makeText(getApplicationContext(), "Erro ao Logar", Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                        );
            }
        });
        // Login Google
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("565988129852-uo4iedbmda94tup63o7o57r3d3imakd7.apps.googleusercontent.com")
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, options);
        FirebaseUser usuario = objAutenticar.getCurrentUser();
        if (usuario != null) {
            usuarioEmpresa = new EmpresaRequest(usuario.getDisplayName(), usuario.getEmail(), usuario.getPhotoUrl().toString());
        }
        //fazer onClick
        ((SignInButton) findViewById(R.id.loginWithGoogle)).setOnClickListener(v -> {
            //Abrir Tela Google
            Intent intent = googleSignInClient.getSignInIntent();
            telaGoogle.launch(intent);
        });
    }
}