package com.easv.wishme.wishme_android.dal;

import com.google.firebase.auth.FirebaseAuth;

public class AuthenticationHelper {
    private FirebaseAuth mAuth;

    public AuthenticationHelper() {
        mAuth = FirebaseAuth.getInstance();
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public void signOut() {
        mAuth.signOut();
    }
}
