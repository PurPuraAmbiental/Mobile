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

import com.purpura.app.configuration.Methods;
import com.purpura.app.R;
import com.purpura.app.databinding.FragmentAccountBinding;
import com.purpura.app.ui.screens.EditAdresses;
import com.purpura.app.ui.screens.EditPixKeys;
import com.purpura.app.ui.screens.Login;

public class AccountFragment extends Fragment {

    Methods methods = new Methods();
    Login login = new Login();
    private FragmentAccountBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AccountViewModel notificationsViewModel =
                new ViewModelProvider(this).get(AccountViewModel.class);

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // ----- Views ----- //
        //Meus pedidos
        ImageView myOrdersIcon = binding.accountBagIcon;
        TextView myOrdersText = binding.accountBagText;
        //Meus produtos
        ImageView myProductsIcon = binding.accountBagIcon;
        TextView myProductsText = binding.accountProductsText;
        //Meus endereços
        ImageView myAddressesIcon = binding.accountLocationIcon;
        TextView myAddressesText = binding.accountLocationText;
        //Minhas chaves pix
        ImageView editPixKeysIcon = binding.accountPixcon;
        TextView editPixKeys = binding.accountPixText;
        //Alterar senha
        ImageView changePasswordIcon = binding.accountPasswordIcon;
        TextView changePassword = binding.accountPasswordText;
        //Editar perfil
        ImageView editProfileIcon = binding.accountProfileImage;
        TextView editProfile = binding.accountProfileText;
        //Sair da conta
        ImageView logOutIcon = binding.accountLogOutIcon;
        TextView logOut = binding.accountLogOutText;

        // ----- SetOnClickListeners ----- //

        editPixKeys.setOnClickListener(v -> methods.openScreenFragments(this, EditPixKeys.class));
        editPixKeysIcon.setOnClickListener(v -> methods.openScreenFragments(this, EditPixKeys.class));

        myAddressesIcon.setOnClickListener(v -> methods.openScreenFragments(this, EditAdresses.class));
        myAddressesText.setOnClickListener(v -> methods.openScreenFragments(this, EditAdresses.class));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}