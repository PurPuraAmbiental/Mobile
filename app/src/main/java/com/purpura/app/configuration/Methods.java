package com.purpura.app.configuration;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.purpura.app.R;
import com.purpura.app.ui.screens.accountFeatures.FirstFragment;

public class Methods {

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

    public void abrirPopUp(Context context, Runnable confirmAction, Runnable cancelAction) {
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



    public static void copyText(Context context, String texto) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("texto", texto);
        clipboard.setPrimaryClip(clip);

        Toast.makeText(context, "Copiado para a área de transferência!", Toast.LENGTH_SHORT).show();
    }

}
