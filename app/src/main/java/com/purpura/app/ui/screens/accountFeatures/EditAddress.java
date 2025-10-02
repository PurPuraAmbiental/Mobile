package com.purpura.app.ui.screens.accountFeatures;

import android.location.Address;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.purpura.app.R;
import com.purpura.app.adapters.AdreesAdapter;
import com.purpura.app.adapters.PixKeyAdapter;
import com.purpura.app.configuration.Methods;
import com.purpura.app.model.PixKey;
import com.purpura.app.remote.service.MongoService;
import com.purpura.app.ui.account.AccountFragment;
import com.purpura.app.ui.screens.errors.GenericError;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAdrees extends AppCompatActivity {

    Methods methods = new Methods();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_adresses);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView backButton = findViewById(R.id.editAdressesBackButton);
        RecyclerView recyclerView = findViewById(R.id.editAdressesRecycleView);

        MongoService mongoService = new MongoService();

        backButton.setOnClickListener(v -> methods.openScreenActivity(this, AccountFragment.class));

        try {
            FirebaseFirestore.getInstance()
                    .collection("empresa")
                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .get()
                    .addOnSuccessListener(document -> {
                        if (document.exists()) {
                            String cnpj = document.getString("cnpj");
                            Call<List<Address>> call = mongoService.getAllAdrees(cnpj);
                            call.enqueue(new Callback<List<Address>>() {
                                @Override
                                public void onResponse(Call<List<Address>> call, Response<List<PixKey>> response) {
                                    if (response.isSuccessful()) {
                                        List<Address> address = response.body();
                                        PixKeyAdapter adapter = new AdreesAdapter(address);
                                        recyclerView.setAdapter(adapter);
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<Address>> call, Throwable t) {
                                    methods.openScreenActivity(EditAdrees.this, GenericError.class);
                                }
                            });
                        }
                    });
        } catch (Exception e) {
            methods.openScreenActivity(EditAdrees.this, GenericError.class);
            throw new RuntimeException(e);
        }

    }
}