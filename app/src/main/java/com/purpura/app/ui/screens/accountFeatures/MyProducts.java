package com.purpura.app.ui.screens.accountFeatures;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.purpura.app.R;
import com.purpura.app.adapters.residues.ResiduesAdapter;
import com.purpura.app.configuration.Methods;
import com.purpura.app.databinding.ActivityMyProductsBinding;
import com.purpura.app.model.Residue;
import com.purpura.app.remote.service.MongoService;
import com.purpura.app.ui.screens.productRegister.RegisterProduct;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyProducts extends AppCompatActivity {

    Methods methods = new Methods();
    private RecyclerView recyclerView;
    MongoService mongoService = new MongoService();

    private AppBarConfiguration appBarConfiguration;
    private ActivityMyProductsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMyProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_my_prroducts);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Inicializa RecyclerView antes de usar
        recyclerView = findViewById(R.id.myProductsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        binding.fab.setOnClickListener(v -> {
            methods.openScreenActivity(this, RegisterProduct.class);
        });

        mongoService.getAllResiduos("17424290000101").enqueue(new Callback<List<Residue>>() {
            @Override
            public void onResponse(Call<List<Residue>> call, Response<List<Residue>> response) {
                if (response.isSuccessful()) {
                    List<Residue> residues = response.body();
                }
                else {
                    Toast.makeText(MyProducts.this, "Erro ao buscar resíduos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Residue>> call, Throwable t) {

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.setAdapter(new ResiduesAdapter(new ArrayList<Residue>()));
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_my_prroducts);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}