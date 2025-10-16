package com.purpura.app.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.purpura.app.R;
import com.purpura.app.adapters.mongo.HomeAdapter;
import com.purpura.app.adapters.postgres.NewsAdapter;
import com.purpura.app.configuration.Methods;
import com.purpura.app.model.mongo.Residue;
import com.purpura.app.model.postgres.news.News;
import com.purpura.app.remote.service.MongoService;
import com.purpura.app.remote.service.PostgresService;
import com.purpura.app.ui.screens.errors.GenericError;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment {

    private RecyclerView recyclerView;
    private NewsAdapter adapter;

    Methods methods = new Methods();
    private final PostgresService postgresService = new PostgresService();
    private Call<List<News>> newsCall;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.newsFragmentReciclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        adapter = new NewsAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        loadResidues(this);
    }

    private void loadResidues(Fragment fragment) {
        try {
            postgresService.getAllNotifications().enqueue(new Callback<List<News>>() {
                @Override
                public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                    if (!isAdded()) return;
                    if (response.isSuccessful() && response.body() != null) {
                        adapter.updateList(response.body());
                    } else {
                        methods.openScreenFragments(fragment, GenericError.class);
                    }
                }

                @Override
                public void onFailure(Call<List<News>> call, Throwable t) {
                    methods.openScreenFragments(NewsFragment.this, GenericError.class);
                }

            });
        } catch (Exception ignored) {
            methods.openScreenFragments(NewsFragment.this, GenericError.class);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (newsCall != null) {
            newsCall.cancel();
            newsCall = null;
        }
    }
}
