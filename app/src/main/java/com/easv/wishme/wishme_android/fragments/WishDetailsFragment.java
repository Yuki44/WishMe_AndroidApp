package com.easv.wishme.wishme_android.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.easv.wishme.wishme_android.R;
import com.easv.wishme.wishme_android.dialogfragments.ChangePhotoDialog;
import com.easv.wishme.wishme_android.entities.Wish;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class WishDetailsFragment extends Fragment {

    private static final String TAG = "WishDetailsFragment";
    private Wish wishToDisplay;
    private TextView mNameInfo, mWishPrice, mWebsiteTxt, mDescriptionTxt;
    private ImageView mWishImage, mIvCheckMark, mIvEdit;
    private RatingBar mRatingBar;
    private Toolbar toolbar;
    private ProgressBar mProgressBar;
    public WishDetailsFragment() {
        super();
        setArguments(new Bundle());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wish_detail, container, false);
        wishToDisplay = getWishFromBundle();
        toolbar = view.findViewById(R.id.wishToolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        mNameInfo = (TextView) view.findViewById(R.id.name_Info);
        mWishPrice = (TextView) view.findViewById(R.id.wishPrice);
        mWebsiteTxt = (TextView) view.findViewById(R.id.websiteTxt);
        mDescriptionTxt = (TextView) view.findViewById(R.id.descriptionTxt);
        mWishImage = (ImageView) view.findViewById(R.id.wishImage);
        mIvCheckMark = (ImageView) view.findViewById(R.id.ivBackArrow);
        mIvEdit = (ImageView) view.findViewById(R.id.ivEdit);
        mRatingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        setUpWish();
        mIvCheckMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Go Back Method here", Toast.LENGTH_SHORT).show();
            }
        });

        mIvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Edit Wish Method here", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.delete_wish_menu, menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.delete_wish:
                Toast.makeText(getContext(), "Wish Deleted method", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUpWish() {
        mNameInfo.setText(wishToDisplay.getName());
        mWishPrice.setText(wishToDisplay.getPrice());
        mWebsiteTxt.setText(wishToDisplay.getLink());
        mDescriptionTxt.setText(wishToDisplay.getDescription());
        mRatingBar.setRating(wishToDisplay.getRating());
        mWishImage.setImageBitmap(wishToDisplay.getImageBitmap());
    }


    private Wish getWishFromBundle(){
        Log.d(TAG, "getUserFromBundle: " + getArguments());
        Bundle bundle = this.getArguments();
        if(bundle != null){
            return bundle.getParcelable("WishDetails");
        } else {
            return null;
        }
    }

}
