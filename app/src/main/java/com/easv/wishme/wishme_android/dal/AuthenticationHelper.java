package com.easv.wishme.wishme_android.dal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.easv.wishme.wishme_android.entities.User;
import com.easv.wishme.wishme_android.fragments.HomeFragment;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class AuthenticationHelper {
    private static final String TAG = "AuthenticationHelper";

    private FirebaseAuth mAuth;
    private User user;
    private Bitmap bitmap;
    FirebaseStorage storage = FirebaseStorage.getInstance("gs://wishme-a73d1.appspot.com/");


    // Create a storage reference from our app
    StorageReference storageRef = storage.getReference();


    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public AuthenticationHelper() {
        mAuth = FirebaseAuth.getInstance();
        user  = new User();
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }



    public void signOut() {
        SignUpStep2.mSelectedImage = null;
        HomeFragment.mSelectedImage = null;
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

    public Bitmap createProfileImage(final Bitmap bitmap, final ICallBack callBack){
        StorageReference userRef = storageRef.child("profile-images/" + getmAuth().getUid());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = userRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                callBack.onFinishGetImage(bitmap);
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
            }
        });
        return bitmap;

    }
    public Bitmap getProfileImage(final ICallBack callBack){
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        if(fbUser != null){
        StorageReference islandRef = storageRef.child("profile-images/" + getmAuth().getUid());

        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                //HomeFragment.mSelectedImage = bitmap;
                callBack.onFinishGetImage(bitmap);
                Log.d(TAG, bitmap.toString());
            }
        });

          return bitmap;

    }else {
            return null;
        }
    }
}