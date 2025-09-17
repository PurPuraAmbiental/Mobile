package com.purpura.app.configuration;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class Methods {

    public void openScreenActivity(Activity actualScreen, Class<?> nextScreen) {
        Intent route = new Intent(actualScreen, nextScreen);
        actualScreen.startActivity(route);
    }

    public void openScreenFragments(Fragment actualScreen, Class<?> nextScreen) {
        if (actualScreen.getContext() != null) {
            Intent route = new Intent(actualScreen.getContext(), nextScreen);
            actualScreen.startActivity(route);
        }
    }

    public static void copyText(Context context, String texto) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("texto", texto);
        clipboard.setPrimaryClip(clip);

        Toast.makeText(context, "Copiado para a área de transferência!", Toast.LENGTH_SHORT).show();
    }

}
