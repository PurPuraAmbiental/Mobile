package com.purpura.app.ui.screens;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.purpura.app.R;
import com.purpura.app.adapters.ProductPageAdapter;
import com.purpura.app.configuration.Methods;
import com.purpura.app.model.Residue;
import com.purpura.app.remote.service.MongoService;
import com.purpura.app.ui.screens.errors.InternetError;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductPage extends AppCompatActivity {

    private final Methods methods = new Methods();
    private final MongoService mongoService = new MongoService();

    private RecyclerView recycler;
    @Nullable private Residue residue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_page_host);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });

        recycler = findViewById(R.id.recyclerViewProductPage);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        // 1) Pega o Residue e um possível CNPJ vindo por Intent
        Bundle env = getIntent().getExtras();
        String cnpjFromIntent = env != null ? env.getString("cnpj", "") : "";
        Serializable ser = env != null ? env.getSerializable("residue") : null;
        if (ser instanceof Residue) residue = (Residue) ser;

        if (residue == null) {
            methods.openScreenActivity(this, InternetError.class);
            return;
        }

        // 2) CNPJ efetivo = do Residue ORIGINAL ou do Intent (não confie no refresh do model)
        String cnpjEffective = firstNonEmpty(residue.getCnpj(), cnpjFromIntent);

        // 3) Se não temos CNPJ ou ID pra refresh, sobe direto o adapter com o que tem
        boolean canRefreshResidue = notEmpty(cnpjEffective) && notEmpty(residue.getId());
        if (!canRefreshResidue) {
            recycler.setAdapter(new ProductPageAdapter(residue, cnpjEffective));
            return;
        }

        // 4) (Opcional) Atualiza o residue pelo backend, mas SEM depender de cnpj do model depois
        mongoService.getResidueById(cnpjEffective, residue.getId())
                .enqueue(new Callback<Residue>() {
                    @Override public void onResponse(Call<Residue> call, Response<Residue> res) {
                        // mesmo que o body venha sem cnpj mapeado, mantemos cnpjEffective
                        Residue finalResidue = (res.isSuccessful() && res.body() != null) ? res.body() : residue;
                        recycler.setAdapter(new ProductPageAdapter(finalResidue, cnpjEffective));
                    }
                    @Override public void onFailure(Call<Residue> call, Throwable t) {
                        recycler.setAdapter(new ProductPageAdapter(residue, cnpjEffective));
                    }
                });
    }

    private static String firstNonEmpty(String a, String b) {
        if (notEmpty(a)) return a;
        if (notEmpty(b)) return b;
        return "";
    }

    private static boolean notEmpty(String s) { return s != null && !s.trim().isEmpty(); }
}
