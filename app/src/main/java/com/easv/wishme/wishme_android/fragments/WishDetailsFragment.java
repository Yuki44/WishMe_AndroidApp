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
    private EditText mWishName, mWishPrice, mWishLink, mWishDescription;
    private ImageView mImageView;
    private Toolbar toolbar;
    public static Bitmap mSelectedImage;
    private ScrollView mScrollView;
    private ProgressBar mProgressBar;
    private FirebaseFirestore db;
    private CollectionReference mDocRef = FirebaseFirestore.getInstance().collection("wish");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wish_detail, container, false);
        mWishName = view.findViewById(R.id.nameET);
        mWishPrice = view.findViewById(R.id.wishPriceET);
        mWishLink = view.findViewById(R.id.wishLinkET);
        //mWishDescription = view.findViewById(R.id.descriptionET);
        mImageView = view.findViewById(R.id.wishImage);
       /*
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePic();
            }
        });
        */
        toolbar = view.findViewById(R.id.editWishToolbar);

        mScrollView = view.findViewById(R.id.ScrollView);
        mProgressBar = view.findViewById(R.id.progressBar);

        ImageView ivBackArrow = view.findViewById(R.id.ivBackArrow);
        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked back arrow.");
                goToWishesFragment();
            }
        });

       // ImageView ivCheckMark = view.findViewById(R.id.ivCheckMark);
        /*
        ivCheckMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: saving the edited profile.");
                if (!mWishName.getText().toString().isEmpty()
                        && !mWishPrice.getText().toString().isEmpty()
                        && !mWishLink.getText().toString().isEmpty()
                        //&& !mmWishDescription.getText().toString().isEmpty()
                        ) {
                    Log.d(TAG, "onClick: saving edited Wish: " + mWishName.getText().toString());
                    updateWish();
                }
            }
        });
*/
        initProgressBar();
        setWishInfo();
        setProfileImage();

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        return view;
    }

    private void changePic() {
        Log.d(TAG, "onClick: opening dialog to choose new photo");
        ChangePhotoDialog dialog = new ChangePhotoDialog();
        dialog.show(getFragmentManager(), getString(R.string.change_photo_dialog));
        dialog.setTargetFragment(WishDetailsFragment.this, 1);
    }

    private void goToWishesFragment() {
        WishesFragment fragment = new WishesFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void setProfileImage(){
       /*
       mSelectedImage = authHelper.getProfileImage(new ICallBack() {

            @Override
            public void onFinish(User user) {}

            @Override
            public void onFinishFireBaseUser(FirebaseUser user) {}

            @Override
            public void onFinishGetImage(Bitmap bitmap) {
                mImageView.setImageBitmap(bitmap);
            }
        });
        */
    }

    private void showProgressBar(){ mProgressBar.setVisibility(View.VISIBLE); }

    private void hideProgressBar(){ mProgressBar.setVisibility(View.GONE); }

    private void initProgressBar(){ mProgressBar.setVisibility(View.INVISIBLE); }

    private void updateWish() {
        /*
               mScrollView.setVisibility(mScrollView.INVISIBLE);
               showProgressBar();
               authHelper.getUserWithInfo(new ICallBack() {
            @Override
            public void onFinish(Wish wish) {
                wish.setName(mWishName.getText().toString());
                wish.setPrice(mWishLink.getText().toString());
                wish.setLink(mWishLink.getText().toString());
                wish.setDescription(mWishDescription).getText.toString();

                authHelper.createUserProfile(user);
                mImageView.setDrawingCacheEnabled(true);
                mImageView.buildDrawingCache();
                Bitmap bitmap = mImageView.getDrawingCache();

                authHelper.createProfileImage(bitmap, new ICallBack() {
                    @Override
                    public void onFinish(User user) {}

                    @Override
                    public void onFinishFireBaseUser(FirebaseUser user) {}

                    @Override
                    public void onFinishGetImage(Bitmap bitmap) {
                        goToWishesFragment();
                    }
                });
                Log.d(TAG, "updateWish: " + wish.toString());
            }
            @Override
            public void onFinishFireBaseUser(FirebaseUser user) {}

            @Override
            public void onFinishGetImage(Bitmap bitmap) {}
        });
        */
    }

    private void setWishInfo() {
        /*
        User user = authHelper.getUserWithInfo(new ICallBack() {
            @Override
            public void onFinish(Wish wish) {
                mWishName.setText(wish.getName());
                mWishPrice.setText(wish.getPrice());
                mWishLink.setText(wish.getLink());
                mWishDescription.setText(wish.getDescription());
            }

            @Override
            public void onFinishFireBaseUser(FirebaseUser user) {
            }

            @Override
            public void onFinishGetImage(Bitmap bitmap) {
            }
        });
        */
    }
}
