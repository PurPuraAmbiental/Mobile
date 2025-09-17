package com.purpura.app.ui.screens;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.purpura.app.R;
import com.purpura.app.configuration.Methods;
import com.purpura.app.model.EmpresaRequest;

public class AddicionalInformacionsRegisterGoogle extends AppCompatActivity {

    Methods methods = new Methods();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_addicional_informacions_register_google);

        // Impede o botão "voltar" físico
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Toast.makeText(AddicionalInformacionsRegisterGoogle.this,
                        "Complete seu cadastro para continuar", Toast.LENGTH_SHORT).show();
            }
        });

        // Ajuste para status bar/navigation bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Botão finalizar cadastro
        Button finalizar = findViewById(R.id.addicionalInformationsEndRegister);
        finalizar.setOnClickListener(v -> salvarInformacoes());
    }

    private void salvarInformacoes() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Toast.makeText(this, "Usuário não encontrado", Toast.LENGTH_SHORT).show();
            return;
        }

        // Dados vindos do Google
        String nome = user.getDisplayName();
        String email = user.getEmail();
        String foto = user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : "";

        // Dados extras preenchidos pelo usuário
        String telefone = ((EditText) findViewById(R.id.addicionalInformationsPhoneNumber))
                .getText().toString().trim();
        String cnpj = ((EditText) findViewById(R.id.AddictionalInformationsCnpj))
                .getText().toString().trim();

        // Valida campos vazios
        if (telefone.isEmpty() || cnpj.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Valida CNPJ
        if (cnpj.length() != 14 || !cnpj.matches("\\d+")) {
            Toast.makeText(this, "CNPJ inválido. Deve ter 14 dígitos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Valida telefone
        if (telefone.length() < 10 || !telefone.matches("\\d+")) {
            Toast.makeText(this, "Telefone inválido. Deve ter no mínimo 10 dígitos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Criação da empresa usando o construtor único
        EmpresaRequest empresa = new EmpresaRequest(cnpj, email, foto, nome, telefone);

        // Debug antes de salvar
        Toast.makeText(this, "Salvando no Firestore...", Toast.LENGTH_SHORT).show();

        // Salva no Firestore usando o UID do usuário
        FirebaseFirestore.getInstance()
                .collection("empresa")
                .document(user.getUid())
                .set(empresa)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Cadastro finalizado!", Toast.LENGTH_SHORT).show();
                    Log.d("FirestoreDebug", "Sucesso ao salvar no Firestore para UID: " + user.getUid());
                    methods.openScreenActivity(this, MainActivity.class);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Log.e("FirestoreDebug", "Erro ao salvar: ", e);
                    Toast.makeText(this,
                            "Erro ao salvar: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                });
    }
}