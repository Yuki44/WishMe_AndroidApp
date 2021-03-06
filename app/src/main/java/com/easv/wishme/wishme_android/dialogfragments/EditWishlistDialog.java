package com.easv.wishme.wishme_android.dialogfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.easv.wishme.wishme_android.R;
import com.easv.wishme.wishme_android.dal.AuthenticationHelper;
import com.easv.wishme.wishme_android.dal.DatabaseHelper;
import com.easv.wishme.wishme_android.entities.Wish;
import com.easv.wishme.wishme_android.interfaces.ICallBackDatabase;
import com.easv.wishme.wishme_android.entities.Wishlist;
import com.easv.wishme.wishme_android.fragments.HomeFragment;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class EditWishlistDialog extends DialogFragment {
    private static final String TAG = "EditWishlistDialog";
    private EditText mNewWishlistName;
    private FirebaseFirestore db;
    private AuthenticationHelper authHelper;
    private DatabaseHelper dataHelper;
    private ProgressBar mProgressBar;
    private LinearLayout linear;
    private Wishlist listFromHome;

    public EditWishlistDialog() {
        super();
        setArguments(new Bundle());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_wishlist_edit, container, false);
        mNewWishlistName = (EditText) view.findViewById(R.id.newWishlistNameTX);
        listFromHome = getWishListFromBundle();
        db = FirebaseFirestore.getInstance();
        authHelper = new AuthenticationHelper();
        dataHelper = new DatabaseHelper();
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        linear = view.findViewById(R.id.linear);
        String oldWishlistName = listFromHome.getwListName();
        mNewWishlistName.setText(oldWishlistName);
        mNewWishlistName.setSelection(mNewWishlistName.getText().length());
        initProgressBar();

        final TextView saveDialog = view.findViewById(R.id.dialogSave);
        saveDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear.setVisibility(View.INVISIBLE);
                showProgressBar();
                if (!mNewWishlistName.getText().equals(null)) {
                    listFromHome.setwListName(mNewWishlistName.getText().toString());
                    saveDialog.setVisibility(View.GONE);
                    dataHelper.editWishList(listFromHome, new ICallBackDatabase() {
                        @Override
                        public void onFinishWishList(Wishlist wList) {
                            getDialog().dismiss();
                            loadHomeFragment();
                        }

                        @Override
                        public void onFinishWishListList(ArrayList list) {

                        }

                        @Override
                        public void onFinishWish(Wish wish) {
                        }

                        @Override
                        public void onFinnishGetWishes(ArrayList list) {
                        }
                    });
                }
            }
        });

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

    private void loadHomeFragment() {
        HomeFragment fragment = new HomeFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void initProgressBar() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    private Wishlist getWishListFromBundle() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            return bundle.getParcelable("WishList");
        } else {
            return null;
        }
    }
}