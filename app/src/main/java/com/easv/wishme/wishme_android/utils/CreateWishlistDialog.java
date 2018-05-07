package com.easv.wishme.wishme_android.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.easv.wishme.wishme_android.R;
import com.easv.wishme.wishme_android.adapters.WishlistAdapter;
import com.easv.wishme.wishme_android.dal.AuthenticationHelper;
import com.easv.wishme.wishme_android.dal.DatabaseHelper;
import com.easv.wishme.wishme_android.dal.ICallBackDatabase;
import com.easv.wishme.wishme_android.entities.Wishlist;
import com.easv.wishme.wishme_android.fragments.HomeFragment;
import com.easv.wishme.wishme_android.fragments.LoginFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CreateWishlistDialog extends DialogFragment {
    private static final String TAG = "CreateWishlistDialog";
    private EditText mNewWishlist;
    private FirebaseFirestore db;
    private AuthenticationHelper authHelper;
    private DatabaseHelper dataHelper;
    private ProgressBar mProgressBar;
    private LinearLayout linear;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_addwishlist, container, false);

        mNewWishlist = (EditText) view.findViewById(R.id.newWishlistTX);

        db = FirebaseFirestore.getInstance();
        authHelper = new AuthenticationHelper();
        dataHelper = new DatabaseHelper();
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        linear = view.findViewById(R.id.linear);
        initProgressBar();




        final TextView saveDialog = view.findViewById(R.id.dialogSave);
        saveDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear.setVisibility(View.INVISIBLE);
            showProgressBar();
if(!mNewWishlist.getText().equals(null)){
    saveDialog.setVisibility(View.GONE);
    Wishlist wList = new Wishlist(mNewWishlist.getText().toString(), authHelper.getmAuth().getUid());
    dataHelper.createWishList(wList, new ICallBackDatabase() {
        @Override
        public void onFinishWishList(Wishlist wList) {
            getDialog().dismiss();
            loadHomeFragment();
        }

        @Override
        public void onFinishWishListList(ArrayList list) {

        }
    });
}
            }
        });






        // Cancel button for closing the dialog
        TextView cancelDialog = view.findViewById(R.id.dialogCancel);
        cancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: closing dialog.");
                getDialog().dismiss();
            }
        });

        return view;
    }

    private void loadHomeFragment(){
        HomeFragment fragment = new HomeFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
    private void showProgressBar(){
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        mProgressBar.setVisibility(View.GONE);
    }

    private void initProgressBar(){

        mProgressBar.setVisibility(View.INVISIBLE);
    }

}