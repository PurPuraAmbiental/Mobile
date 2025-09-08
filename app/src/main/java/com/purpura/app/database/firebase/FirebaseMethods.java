package com.purpura.app.database.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.purpura.app.ui.screens.RegisterOrLogin;
import com.purpura.app.configuration.Methods;
public class FirebaseMethods {

    public void logout() {
        FirebaseAuth.getInstance().signOut();
    }
}
