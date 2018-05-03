package com.easv.wishme.wishme_android.dal;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.easv.wishme.wishme_android.entities.User;
import com.easv.wishme.wishme_android.fragments.SignUpStep2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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
    private static final String TAG = "AuthenticationHelper";

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

    public FirebaseUser signUpNewUser(User user, final ICallBack callBack) {
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            callBack.onFinishFireBaseUser(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        }

                    }
                });
        return mAuth.getCurrentUser();
    }

    public void createUserProfile(User user){
        Log.d(TAG, mAuth.getCurrentUser().getUid());
        db.collection("users").document(mAuth.getUid())
                .set(user.toMap());

    }

}