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

import com.purpura.app.model.Company;
import com.purpura.app.remote.service.MongoService;
import com.purpura.app.ui.screens.Dashboards;
import com.google.firebase.auth.FirebaseAuth;
import com.purpura.app.ui.screens.accountFeatures.MyOrders;
import com.purpura.app.configuration.Methods;
import com.purpura.app.database.firebase.FirebaseMethods;
import com.purpura.app.databinding.FragmentAccountBinding;
import com.purpura.app.ui.screens.accountFeatures.EditAdresses;
import com.purpura.app.ui.screens.accountFeatures.EditPixKeys;
import com.purpura.app.ui.screens.accountFeatures.MyProducts;
import com.purpura.app.ui.screens.autentication.Login;
import com.purpura.app.ui.screens.autentication.RegisterOrLogin;

public class AccountFragment extends Fragment {

    FirebaseAuth objAutenticar = FirebaseAuth.getInstance();
    MongoService service = new MongoService();
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
        //Ver dashboards
        ImageView dashboardIcon = binding.accountFragmentDashboardIcon;
        TextView dashboardText = binding.accountFragmentDasboardText;
        //Alterar senha
        ImageView changePasswordIcon = binding.accountPasswordIcon;
        TextView changePassword = binding.accountPasswordText;
        //Editar perfil
        ImageView editProfileIcon = binding.accountProfileImage;
        TextView editProfileText = binding.accountProfileText;
        //Sair da conta
        ImageView logOutIcon = binding.accountLogOutIcon;
        TextView logOut = binding.accountLogOutText;

        // ----- SetOnClickListeners ----- //

        editPixKeys.setOnClickListener(v -> methods.openScreenFragments(this, EditPixKeys.class));
        editPixKeysIcon.setOnClickListener(v -> methods.openScreenFragments(this, EditPixKeys.class));

        myAddressesIcon.setOnClickListener(v -> methods.openScreenFragments(this, EditAdresses.class));
        myAddressesText.setOnClickListener(v -> methods.openScreenFragments(this, EditAdresses.class));

        changePasswordIcon.setOnClickListener(v -> showPasswordReset());
        changePassword.setOnClickListener(v -> showPasswordReset());

        myOrdersIcon.setOnClickListener(v -> methods.openScreenFragments(this, MyOrders.class));
        myOrdersText.setOnClickListener(v -> methods.openScreenFragments(this, MyOrders.class));

        dashboardIcon.setOnClickListener(v -> methods.openScreenFragments(this, Dashboards.class));
        dashboardText.setOnClickListener(v -> methods.openScreenFragments(this, Dashboards.class));

        myProductsIcon.setOnClickListener(v -> methods.openScreenFragments(this, MyProducts.class));
        myProductsText.setOnClickListener(v -> methods.openScreenFragments(this, MyProducts.class));

        editProfileIcon.setOnClickListener(v -> {
            methods.openUpdateCompanyPopUp(this.getContext());
        });
        editProfileText.setOnClickListener(v -> {
            methods.openUpdateCompanyPopUp(this.getContext());
        });

        logOutIcon.setOnClickListener(v -> {
            methods.openConfirmationPopUp(this.getContext(),
                    () -> logOut(),
                    null);
        });
        logOut.setOnClickListener(v -> {
            methods.openConfirmationPopUp(this.getContext(),
                    () -> logOut(),
                    null);
        });

        return root;
    }

    private void logOut() {
        firebaseMethods.logout();
        methods.openScreenFragments(this, RegisterOrLogin.class);
    }

    private void showPasswordReset() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this.getContext());
        alert.setTitle("Esqueci minha senha");
        alert.setMessage("Entre com seu email para redefinir sua senha");

        EditText editTextEmail = new EditText(this.getContext());
        alert.setView(editTextEmail);

        alert.setPositiveButton("Enviar", (dialog, which) -> {
            objAutenticar.sendPasswordResetEmail(editTextEmail.getText().toString());
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