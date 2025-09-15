package com.purpura.app.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.purpura.app.R;
import com.purpura.app.databinding.FragmentAccountBinding;
import com.purpura.app.ui.screens.Login;

public class AccountFragment extends Fragment {

    Login login = new Login();
    private FragmentAccountBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AccountViewModel notificationsViewModel =
                new ViewModelProvider(this).get(AccountViewModel.class);

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ImageView logoutIcon = root.findViewById(R.id.accountLogoutIcon);
        TextView logoutText = root.findViewById(R.id.accountLogoutText);

//
//        logoutIcon.setOnClickListener(v -> {
//            login.logout();
//        });
//        logoutText.setOnClickListener(v -> {
//            login.logout();
//        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}