package com.easv.wishme.wishme_android.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.easv.wishme.wishme_android.R;
import com.easv.wishme.wishme_android.dal.DatabaseHelper;
import com.easv.wishme.wishme_android.dialogfragments.ChangePhotoDialog;
import com.easv.wishme.wishme_android.entities.User;
import com.easv.wishme.wishme_android.entities.Wish;
import com.easv.wishme.wishme_android.entities.Wishlist;
import com.easv.wishme.wishme_android.interfaces.ICallBack;
import com.easv.wishme.wishme_android.interfaces.ICallBackDatabase;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class WishDetailsFragment extends Fragment {

    private static final String TAG = "WishDetailsFragment";
    private Wish wishToDisplay;
    private TextView mNameInfo, mWishPrice, mWebsiteTxt, mDescriptionTxt;
    private ImageView mWishImage, mIvCheckMark, mIvEdit;
    private RatingBar mRatingBar;
    private Toolbar toolbar;
    private ProgressBar mImageProgressbar, mProgressBar;
    private DatabaseHelper databaseHelper;
    private RelativeLayout mContent;
    public WishDetailsFragment() {
        super();
        setArguments(new Bundle());
    }

    public interface UpdateWish {
        void getWishFromDetailView(Wish wish);
    }
    UpdateWish mUpdateWish;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wish_detail, container, false);
        wishToDisplay = getWishFromBundle();
        toolbar = view.findViewById(R.id.wishToolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        databaseHelper = new DatabaseHelper();
        mNameInfo = (TextView) view.findViewById(R.id.name_Info);
        mWishPrice = (TextView) view.findViewById(R.id.wishPrice);
        mWebsiteTxt = (TextView) view.findViewById(R.id.websiteTxt);
        mDescriptionTxt = (TextView) view.findViewById(R.id.descriptionTxt);
        mWishImage = (ImageView) view.findViewById(R.id.wishImage);
        mIvCheckMark = (ImageView) view.findViewById(R.id.ivBackArrow);
        mIvEdit = (ImageView) view.findViewById(R.id.ivEdit);
        mRatingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        mContent = (RelativeLayout) view.findViewById(R.id.relLayout2);
        mImageProgressbar = view.findViewById(R.id.imageProgressBar);
        mProgressBar = view.findViewById(R.id.progressBar);
        initImageProgressBar();
        initProgressBar();
        setUpWish();
        mIvCheckMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });

        mIvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUpdateWish.getWishFromDetailView(wishToDisplay);
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
                deleteWish(wishToDisplay);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteWish(Wish wish) {
        toolbar.setVisibility(View.GONE);
        mContent.setVisibility(View.GONE);
        showProgressBar();
        databaseHelper.deleteWish(wish, new ICallBackDatabase() {
            @Override
            public void onFinishWishList(Wishlist wList) {

            }

            @Override
            public void onFinishWishListList(ArrayList list) {

            }

            @Override
            public void onFinishWish(Wish wish) {
                showProgressBar();
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }

            @Override
            public void onFinnishGetWishes(ArrayList list) {

            }
        });
    }

    private void setUpWish() {
        showImageProgressBar();
        mNameInfo.setText(wishToDisplay.getName());
        mWishPrice.setText(wishToDisplay.getPrice());
        mWebsiteTxt.setText(wishToDisplay.getLink());
        mDescriptionTxt.setText(wishToDisplay.getDescription());
        mRatingBar.setRating(wishToDisplay.getRating());
        databaseHelper.getWishImage(wishToDisplay.getId(), new ICallBack() {
            @Override
            public void onFinish(User user) {

            }

            @Override
            public void onFinishFireBaseUser(FirebaseUser user) {

            }

            @Override
            public void onFinishGetImage(Bitmap bitmap) {
                hideImageProgressBar();
                mWishImage.setImageBitmap(bitmap);
                mRatingBar.setRating(wishToDisplay.getRating());
            }
        });
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


    private Wish getWishFromBundle(){
        Log.d(TAG, "getUserFromBundle: " + getArguments());
        Bundle bundle = this.getArguments();
        if(bundle != null){
            return bundle.getParcelable("WishDetails");
        } else {
            return null;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mUpdateWish = (WishDetailsFragment.UpdateWish)  getActivity();

        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage());

        }
    }

    private void showImageProgressBar(){
        mImageProgressbar.setVisibility(View.VISIBLE);
    }

    private void hideImageProgressBar() {
        mImageProgressbar.setVisibility(View.GONE);
    }

    private void initImageProgressBar() {

        mImageProgressbar.setVisibility(View.INVISIBLE);
    }


}
