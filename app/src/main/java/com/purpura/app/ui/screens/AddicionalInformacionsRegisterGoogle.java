package com.purpura.app.ui.screens;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.purpura.app.R;
import com.purpura.app.model.EmpresaRequest;

public class AddicionalInformacionsRegisterGoogle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_addicional_informacions_register_google);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button finalizar = findViewById(R.id.addicionalInformationsEndRegister);
        finalizar.setOnClickListener(v -> {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user == null) {
                Toast.makeText(this, "Usuário não encontrado", Toast.LENGTH_SHORT).show();
                return;
            }

            // Dados do Google
            String nome = user.getDisplayName();
            String email = user.getEmail();
            String foto = user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : "";

            // Dados extras preenchidos
            String telefone = ((EditText) findViewById(R.id.addicionalInformationsPhoneNumber))
                    .getText().toString().trim();
            String cnpj = ((EditText) findViewById(R.id.AddictionalInformationsCnpj))
                    .getText().toString().trim();

            if (telefone.isEmpty() || cnpj.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            EmpresaRequest empresa = new EmpresaRequest(nome, email, foto, telefone, cnpj);

            // Salva no Firestore usando UID como documento
            FirebaseFirestore.getInstance()
                    .collection("empresas")
                    .document(user.getUid())
                    .set(empresa)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Cadastro finalizado!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Erro ao salvar: " + e.getMessage(), Toast.LENGTH_LONG).show()
                    );
        });
    }
}
