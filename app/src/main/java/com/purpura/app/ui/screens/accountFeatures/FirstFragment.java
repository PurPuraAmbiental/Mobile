package com.purpura.app.ui.screens.accountFeatures;

import static android.content.Intent.getIntent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.purpura.app.adapters.ResidueAdapter;
import com.purpura.app.configuration.Methods;
import com.purpura.app.databinding.FragmentFirstBinding;
import com.purpura.app.model.Residue;
import com.purpura.app.remote.service.MongoService;
import com.purpura.app.ui.account.AccountFragment;
import com.purpura.app.ui.screens.MainActivity;
import com.purpura.app.ui.screens.errors.GenericError;
import com.purpura.app.ui.screens.productRegister.RegisterPixKey;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    Methods methods = new Methods();
    MongoService mongoService = new MongoService();
    private RecyclerView myProductsRecyclerView;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);

        myProductsRecyclerView = binding.myProductsRecyclerView;
        myProductsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        try{
            FirebaseFirestore.getInstance()
                    .collection("empresa")
                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .get()
                    .addOnSuccessListener(document -> {
                        if (document.exists()) {
                            String cnpj = document.getString("cnpj");
                            Call<List<Residue>> call = mongoService.getAllResiduos(cnpj);
                            call.enqueue(new Callback<List<Residue>>() {
                                @Override
                                public void onResponse(Call<List<Residue>> call, Response<List<Residue>> response) {
                                    if(response.isSuccessful()){
                                        List<Residue> residues = response.body();
                                        myProductsRecyclerView.setAdapter(new ResidueAdapter(residues));
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<Residue>> call, Throwable t) {
                                    methods.openScreenFirstFragment(FirstFragment.this, GenericError.class);
                                }
                            });
                        }
                    });
        } catch (Exception e) {
            methods.openScreenFirstFragment(FirstFragment.this, GenericError.class);
            throw new RuntimeException(e);
        }

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.myProductsBackButton.setOnClickListener(v -> methods.openScreenFirstFragment(FirstFragment.this, AccountFragment.class));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}