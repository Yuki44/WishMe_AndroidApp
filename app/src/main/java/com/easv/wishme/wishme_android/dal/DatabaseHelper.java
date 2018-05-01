package com.easv.wishme.wishme_android.dal;

import com.easv.wishme.wishme_android.entities.Wish;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class DatabaseHelper {

 private static final String TAG = "DatabaseHelper";

 private FirebaseFirestore mFirestore;

 public DatabaseHelper() {
 }

// -----------------------------------------------------------------------------
 // -----------------------------------------------------------------------------
 //                  GET ALL WISHES

 /**
  * Retrieve all contacts from database
  *
  * @return
  */
 public ArrayList<Wish> getAllWishes() {
  final ArrayList<Wish> list = new ArrayList<Wish>();


  // THIS DOESN'T WORK.... WEIRD...(?)


//  mFirestore = FirebaseFirestore.getInstance();
//  mFirestore.collection("wish")
//          .get()
//          .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//           @Override
//           public void onComplete(@NonNull Task<QuerySnapshot> task) {
//            if (task.isSuccessful()) {
//             for (QueryDocumentSnapshot document : task.getResult()) {
////              Log.d(TAG, document.getId() + " => " + document.getData());
//              Wish wish = document.toObject(Wish.class);
//              list.add(wish);
//             }
//            } else {
//             Log.d(TAG, "Error getting documents: ", task.getException());
//            }
//           }
//          });
  return list;
 }
}
