package com.purpura.app.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.purpura.app.ui.screens.MyOrders;
import com.purpura.app.configuration.Methods;
import com.purpura.app.R;
import com.purpura.app.database.firebase.FirebaseMethods;
import com.purpura.app.databinding.FragmentAccountBinding;
import com.purpura.app.ui.screens.EditAdresses;
import com.purpura.app.ui.screens.EditPixKeys;
import com.purpura.app.ui.screens.Login;
import com.purpura.app.ui.screens.RegisterOrLogin;

public class AccountFragment extends Fragment {

    FirebaseAuth objAutenticar = FirebaseAuth.getInstance();
    Methods methods = new Methods();
    FirebaseMethods firebaseMethods = new FirebaseMethods();
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

        logOutIcon.setOnClickListener(v -> {
            firebaseMethods.logout();
            methods.openScreenFragments(this, RegisterOrLogin.class);
        });
        logOut.setOnClickListener(v -> {
            firebaseMethods.logout();
            methods.openScreenFragments(this, RegisterOrLogin.class);
        });

        changePasswordIcon.setOnClickListener(v -> showPasswordReset());
        changePassword.setOnClickListener(v -> showPasswordReset());

        myOrdersIcon.setOnClickListener(v -> methods.openScreenFragments(this, MyOrders.class));
        myOrdersText.setOnClickListener(v -> methods.openScreenFragments(this, MyOrders.class));

        return root;
    }

    private void showPasswordReset() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this.getContext());
        alert.setTitle("Esqueci minha senha");
        alert.setMessage("Entre com seu email para redefinir sua senha");

        EditText editTextEmail = new EditText(this.getContext());
        alert.setView(editTextEmail);

        alert.setPositiveButton("Enviar", (dialog, which) -> {
            String email = editTextEmail.getText().toString();
            objAutenticar.sendPasswordResetEmail(email);
            Toast.makeText(this.getContext(), "Email Enviado", Toast.LENGTH_LONG).show();
        });
        alert.show();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}