package com.purpura.app.ui.screens.productRegister;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.purpura.app.R;
import com.purpura.app.configuration.Methods;
import com.purpura.app.model.Residue;
import com.purpura.app.remote.service.MongoService;
import com.purpura.app.ui.account.AccountFragment;
import com.purpura.app.ui.screens.accountFeatures.MyProducts;

public class RegisterProduct extends AppCompatActivity {

    Methods methods = new Methods();
    MongoService mongoService = new MongoService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.constraintLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView backButton = findViewById(R.id.registerProductBackButton);
        Button continueButton = findViewById(R.id.registerProductAddProductButton);

        EditText name = findViewById(R.id.registerProductName);
        EditText description = findViewById(R.id.registerProductDescription);
        EditText price = findViewById(R.id.registerProductPrice);
        EditText weight = findViewById(R.id.registerProductWeight);
        EditText unitMeasure = findViewById(R.id.registerProductWeightType);
        EditText quantity = findViewById(R.id.registerProductQuantity);
        EditText image = findViewById(R.id.registerProductImage);
        if(name != null && weight != null && price != null && unitMeasure != null && quantity != null && image != null){
            Residue residue = new Residue(
                    null,
                    name.getText().toString(),
                    description.getText().toString(),
                    Double.parseDouble(weight.toString()),
                    Double.parseDouble(price.toString()),
                    Integer.parseInt(quantity.toString()),
                    unitMeasure.getText().toString(),
                    image.getText().toString()
            );

            continueButton.setOnClickListener(v -> {
                try{
                    FirebaseFirestore.getInstance()
                            .collection("empresa")
                            .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .get()
                            .addOnSuccessListener(document -> {
                                if (document.exists()) {
                                    String cnpj = document.getString("cnpj");
                                    mongoService.createResidue(cnpj, residue, this);
                                    methods.openScreenActivity(this, RegisterAdress.class);
                                }
                            });

                }catch (Exception e){
                    e.printStackTrace();
                }
            });
        } else{
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
        }

        backButton.setOnClickListener(v -> methods.openScreenActivity(this, MyProducts.class));

    }
}