package com.easv.wishme.wishme_android.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.easv.wishme.wishme_android.R;
import com.easv.wishme.wishme_android.dialogfragments.ChangePhotoDialog;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class WishDetailsFragment extends Fragment {

    private static final String TAG = "EditWishFragment";
    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wish_detail, container, false);

        toolbar = view.findViewById(R.id.wishToolbar);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        return view;
    }
}
