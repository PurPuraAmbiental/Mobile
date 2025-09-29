package com.purpura.app.remote.firebase;

import com.google.firebase.auth.FirebaseAuth;

public class FirebaseMethods {

    public void logout() {
        FirebaseAuth.getInstance().signOut();
    }
}
