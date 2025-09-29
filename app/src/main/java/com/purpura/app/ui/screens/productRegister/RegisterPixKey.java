package com.purpura.app.ui.screens.productRegister;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.purpura.app.configuration.Methods;
import com.purpura.app.R;
import com.purpura.app.model.PixKey;
import com.purpura.app.remote.service.MongoService;
import com.purpura.app.ui.account.AccountFragment;

public class RegisterPixKey extends AppCompatActivity {

    Methods methods = new Methods();
    MongoService mongoService = new MongoService();
    Bundle bundle = getIntent().getExtras();
    String CNPJ = bundle.getString("CNPJ");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_pix_key);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView backButton = findViewById(R.id.registerAdressBackButton);
        Button continueButton = findViewById(R.id.registerPixKeyAddPixKeyButton);
        EditText pixKeyInput = findViewById(R.id.registerPixKeyInput);
        EditText pixKeyNameInput = findViewById(R.id.registerPixKeyNameInput);
        PixKey pixKey = new PixKey(pixKeyNameInput.getText().toString(), pixKeyInput.getText().toString());


        backButton.setOnClickListener(v -> methods.openScreenActivity(this, RegisterAdress.class));
        continueButton.setOnClickListener(v -> {
            try{
                mongoService.createPixKey(CNPJ, pixKey, this);
            }catch (Exception e){
                e.printStackTrace();
            }
        });

    }
}