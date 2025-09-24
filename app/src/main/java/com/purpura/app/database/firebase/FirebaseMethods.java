package com.purpura.app.database.firebase;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseMethods {

    public void logout() {
        FirebaseAuth.getInstance().signOut();
    }
}
