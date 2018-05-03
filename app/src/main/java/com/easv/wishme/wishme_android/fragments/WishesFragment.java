package com.easv.wishme.wishme_android.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.easv.wishme.wishme_android.R;
import com.easv.wishme.wishme_android.adapters.WishAdapter;
import com.easv.wishme.wishme_android.entities.Wish;
import com.easv.wishme.wishme_android.entities.Wishlist;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class WishesFragment extends android.support.v4.app.Fragment implements HomeFragment.OnWishlistItemClicked {


    private static final String TAG = "WishesFragment";
    public ListView mWishList;
    public WishAdapter wishAdapter;
    private TextView mNoWishes;
    private ArrayList<Wish> wishList;
    private FirebaseFirestore db;
    private CollectionReference mDocRef = FirebaseFirestore.getInstance().collection("wish");


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        mNoWishes = view.findViewById(R.id.textNoWishes);
        mWishList = view.findViewById(R.id.wishesList);
        db = FirebaseFirestore.getInstance();
//        this.setTargetFragment(WishesFragment.this, 0);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mNoWishes.setText("Loading...");
        wishList = new ArrayList<>();
        db.collection("wish")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            mNoWishes.setText("");
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Wish wish = document.toObject(Wish.class);
                                wishList.add(wish);
                            }
                            wishAdapter = new WishAdapter(getActivity(), R.layout.wish_item, wishList, "https://");
                            mWishList.setAdapter(wishAdapter);
                            sortListByName();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            mNoWishes.setText("Something went wrong :(");
                        }
                    }
                });
    }

    private void sortListByName() {
        Collections.sort(wishList, new Comparator<Wish>() {
            @Override
            public int compare(Wish o1, Wish o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
    }


    private void checkIfChanged() {

    }

    @Override
    public void getWishlistItemClicked(Wishlist wList) {
        Log.d(TAG, "getWishlistItemClicked: get the clicked Wishlist" + wList);

    }

//private void setWishList(){


    // THIS DOESN'T WORK BECAUSE OF SOME DARK UNKNOWN MAGIC


//        mNoWishes.setText("Loading...");
//    wishList = new ArrayList<>();
//    DatabaseHelper databaseHelper = new DatabaseHelper();
//    wishList = databaseHelper.getAllWishes();
//    Collections.sort(wishList, new Comparator<Wish>() {
//        @Override
//        public int compare(Wish o1, Wish o2) {
//            return o1.getName().compareToIgnoreCase(o2.getName());
//        }
//    });
//
//    if (wishList.isEmpty()) {
//        Toast.makeText(getActivity(), "Add some wishes!", Toast.LENGTH_SHORT).show();
//        mNoWishes.setText("No wishes to show");
//    } else {
//        mNoWishes.setText("");
//    }
//    wishAdapter = new WishAdapter(getActivity(), R.layout.wish_item, wishList, "http://");
//    mNoWishes.setText("");
//    mWishList.setAdapter(wishAdapter);
//
//}




}
