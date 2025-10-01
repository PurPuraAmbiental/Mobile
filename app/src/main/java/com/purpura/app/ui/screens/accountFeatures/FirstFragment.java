package com.purpura.app.ui.screens.accountFeatures;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.purpura.app.configuration.Methods;
import com.purpura.app.databinding.FragmentFirstBinding;
import com.purpura.app.ui.account.AccountFragment;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    Methods methods = new Methods();

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState

    ) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = FragmentFirstBinding.inflate(inflater, container, false);
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