package com.easv.wishme.wishme_android.dal;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.easv.wishme.wishme_android.entities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

public class AuthenticationHelper {
    private FirebaseAuth mAuth;
    private User user;

    FirebaseFirestore db = FirebaseFirestore.getInstance();



    public AuthenticationHelper() {
        mAuth = FirebaseAuth.getInstance();
        user  = new User();
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }



    public void signOut() {
        mAuth.signOut();
    }

    public User getUserWithInfo(final ICallBack callBack) {
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        if (fbUser != null) {
            DocumentReference docRef = db.collection("users").document(fbUser.getUid());

            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    user = documentSnapshot.toObject(User.class);
                    callBack.onFinish(user);
                    Log.d("YYY", user.toString());
                }
            });
            Log.d("YYY", user.toString());
            return user;
        } else {
            return null;

        }


    }

}