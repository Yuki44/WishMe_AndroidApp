package com.easv.wishme.wishme_android.dal;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.easv.wishme.wishme_android.R;
import com.easv.wishme.wishme_android.adapters.WishlistAdapter;
import com.easv.wishme.wishme_android.entities.Wish;
import com.easv.wishme.wishme_android.entities.Wishlist;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class DatabaseHelper {

 private static final String TAG = "DatabaseHelper";
 FirebaseStorage storage = FirebaseStorage.getInstance("gs://wishme-a73d1.appspot.com/");
 StorageReference storageRef = storage.getReference();
 FirebaseFirestore db = FirebaseFirestore.getInstance();
 private ArrayList wishListList;

 private AuthenticationHelper authHelper;

    public DatabaseHelper() {
      authHelper = new AuthenticationHelper();
    }

    public void createWishList(final Wishlist wList, final ICallBackDatabase callBackDatabase)
 {
  db.collection("wishlist")
          .add(wList)
          .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
           @Override
           public void onSuccess(DocumentReference documentReference) {
            Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
            callBackDatabase.onFinishWishList(wList);
           }
          })
          .addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
            Log.w(TAG, "Error adding document", e);
           }
          });
 }
    public void editWishList(final Wishlist wList, final ICallBackDatabase callBackDatabase)
    {
        db.collection("wishlist").document(wList.getId())
             .set(wList);
        callBackDatabase.onFinishWishList(wList);
    }
 public void getWishLists(final ICallBackDatabase callBackDatabase){
     wishListList = new ArrayList<>();
     db.collection("wishlist").whereEqualTo("owner", authHelper.getmAuth().getUid().toString())
             .get()
             .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                 @Override
                 public void onComplete(@NonNull Task<QuerySnapshot> task) {
                     if (task.isSuccessful()) {
                         for (QueryDocumentSnapshot document : task.getResult()) {
                             Log.d(TAG, document.getId() + " => " + document.getData());
                             Wishlist wishlist = document.toObject(Wishlist.class);
                             wishlist.setId(document.getId());
                             wishListList.add(wishlist);
                             callBackDatabase.onFinishWishListList(wishListList);
                         }
                     } else {
                         Log.d(TAG, "Error getting documents: ", task.getException());
                     }
                 }
             });
 }

 public void addWish(){

 }


}

