package com.easv.wishme.wishme_android.dal;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.easv.wishme.wishme_android.R;
import com.easv.wishme.wishme_android.adapters.WishAdapter;
import com.easv.wishme.wishme_android.entities.User;
import com.easv.wishme.wishme_android.entities.Wish;
import com.easv.wishme.wishme_android.entities.Wishlist;
import com.easv.wishme.wishme_android.fragments.SignUpStep1;
import com.easv.wishme.wishme_android.interfaces.ICallBack;
import com.easv.wishme.wishme_android.interfaces.ICallBackDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
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
     db.collection("wishlist").whereEqualTo("owner", authHelper.getmAuth().getUid())
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

 public void createWish(final Wish wish, final Bitmap bitmap, final ICallBackDatabase callBackDatabase ){
    db.collection("wish")
            .add(wish)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    createWishImage(bitmap, documentReference.getId(), new ICallBack() {
                        @Override
                        public void onFinish(User user) {

                        }

                        @Override
                        public void onFinishFireBaseUser(FirebaseUser user) {

                        }

                        @Override
                        public void onFinishGetImage(Bitmap bitmap) {
                            callBackDatabase.onFinishWish(wish);

                        }
                    });

                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "Error adding document", e);
                }
            });
 }
    public Bitmap createWishImage(final Bitmap bitmap, String wishId, final ICallBack callBack){
        StorageReference userRef = storageRef.child("wish-images/" + wishId);
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
    public void getWishImage(String wishId, final ICallBack callBack){
        if(wishId != null) {

            StorageReference islandRef = storageRef.child("wish-images/" + wishId);

            Log.d("TAGGG", "getWishImage" + wishId);

            final long ONE_MEGABYTE = 1024 * 1024;
            islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {

                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    //HomeFragment.mSelectedImage = bitmap;
                    callBack.onFinishGetImage(bitmap);
                    Log.d("DUNNP", bitmap.toString());
                }
            });
        }
    }

    public void getWish(Wishlist listFromHome, final ICallBackDatabase callBackDatabase){
        final ArrayList list = new ArrayList();
        db.collection("wish").whereEqualTo("owner", listFromHome.getId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        Wish wish = document.toObject(Wish.class);
                        wish.setId(document.getId());
                        list.add(wish);
                    }
                    callBackDatabase.onFinnishGetWishes(list);
                }
            }
        });
    }


}

