package com.purpura.app.configuration;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.purpura.app.R;
import com.purpura.app.model.Company;
import com.purpura.app.remote.service.MongoService;
import com.purpura.app.ui.screens.accountFeatures.FirstFragment;

public class Methods {

    private String hashUser; // variável da classe

    public void loadCompanyHash(Context context) {
        String uid = FirebaseAuth.getInstance().getCurrentUser() != null
                ? FirebaseAuth.getInstance().getCurrentUser().getUid()
                : null;

        if (uid == null) {
            Log.e("Firestore", "Usuário não autenticado");
            return;
        }

        FirebaseFirestore.getInstance()
                .collection("empresa")
                .document(uid)
                .get()
                .addOnSuccessListener(document -> {
                    Company company = document.toObject(Company.class);
                    if (company != null) {
                        hashUser = company.getHashUser(); // ✅ salva o valor aqui
                        Log.d("HASH", "Hash recebido: " + hashUser);
                    } else {
                        hashUser = null;
                        Log.d("HASH", "Empresa não encontrada");
                    }
                })
                .addOnFailureListener(e -> {
                    hashUser = null;
                    Log.e("Firestore", "Erro ao buscar empresa", e);
                });
    }

    String hash = hashUser;

    public String getHash(){
        return hash;
    }

    public void openActivityToMongoService(Context context, Class<?> nextScreen){
        Intent route = new Intent(context, nextScreen);
        context.startActivity(route);
    }

    MongoService mongoService;

    public void openScreenActivity(Activity actualScreen, Class<?> nextScreen) {
        Intent route = new Intent(actualScreen, nextScreen);
        actualScreen.startActivity(route);
    }

    public void openScreenFirstFragment(FirstFragment actualScreen, Class<?> nextScreen) {
        Intent route = new Intent(actualScreen.getContext(), nextScreen);
        actualScreen.startActivity(route);
    }

    public void openScreenFragments(Fragment actualScreen, Class<?> nextScreen) {
        if (actualScreen.getContext() != null) {
            Intent route = new Intent(actualScreen.getContext(), nextScreen);
            actualScreen.startActivity(route);
        }
    }

    public void openConfirmationPopUp(Context context, Runnable confirmAction, Runnable cancelAction) {
        View popupView = LayoutInflater.from(context)
                .inflate(R.layout.confirm_alterations, null);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(popupView)
                .create();

        popupView.findViewById(R.id.popUpConfirmAlterations).setOnClickListener(v -> {
            confirmAction.run();
            dialog.dismiss();
        });

        if (cancelAction == null){
            popupView.findViewById(R.id.popUpCancelAlterations).setVisibility(View.GONE);
        } else {
            popupView.findViewById(R.id.popUpCancelAlterations).setOnClickListener(v -> {
                cancelAction.run();
                dialog.dismiss();
            });
        }

        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(
                    (int)(context.getResources().getDisplayMetrics().widthPixels * 0.85),
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
        }

        dialog.show();
    }

    public void openUpdateCompanyPopUp(Context context){
        View popupView = LayoutInflater.from(context)
            .inflate(R.layout.pop_up_edit_profile_card, null);

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(popupView)
                .create();

        String cnpj = ((EditText) popupView.findViewById(R.id.popUpEditCompanyCnpj)).getText().toString();

        Company companyToSave = new Company(
                null,
                ((EditText) popupView.findViewById(R.id.popUpEditCompanyName)).getText().toString(),
                ((EditText) popupView.findViewById(R.id.popUpEditCompanyEmail)).getText().toString(),
                cnpj,
                ((EditText) popupView.findViewById(R.id.popUpEditCompanyPhone)).getText().toString(),
                ((EditText) popupView.findViewById(R.id.popUpEditCompanyImage)).getText().toString()
        );

        popupView.findViewById(R.id.popUpEditCompanyConfirmAlterations).setOnClickListener(v -> {
            openConfirmationPopUp(
                    context,
                    () -> mongoService.updateCompany(cnpj, companyToSave, context),
                    null);
        });

    }

    public static void copyText(Context context, String texto) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("texto", texto);
        clipboard.setPrimaryClip(clip);

        Toast.makeText(context, "Copiado para a área de transferência!", Toast.LENGTH_SHORT).show();
    }

}
