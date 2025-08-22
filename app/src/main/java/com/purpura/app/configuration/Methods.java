package com.purpura.app.configuration;

import android.app.Activity;
import android.content.Intent;

public class Methods {

    public void openScreen(Activity actualScreen, Class<?> nextScreen) {
        Intent route = new Intent(actualScreen, nextScreen);
        actualScreen.startActivity(route);
    }

}
