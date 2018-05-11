package com.easv.wishme.wishme_android.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.easv.wishme.wishme_android.R;
import com.easv.wishme.wishme_android.activities.MainActivity;
import com.easv.wishme.wishme_android.dal.DatabaseHelper;
import com.easv.wishme.wishme_android.dialogfragments.ChangePhotoDialog;
import com.easv.wishme.wishme_android.entities.Wish;
import com.easv.wishme.wishme_android.entities.Wishlist;
import com.easv.wishme.wishme_android.interfaces.ICallBackDatabase;

import java.util.ArrayList;

public class AddWishFragment extends Fragment {
    private static final String TAG = "AddWishFragment";
    private EditText mNameInfo, mWishPrice, mWebsiteTxt, mDescriptionTxt;
    private TextView mRatingText;
    private ImageView mCameraIcon, mWishImage, mIvCheckMark, mIvBackArrow;
    private RatingBar mRatingBar;
    private float rating;
    private Wishlist mWishList;
    private ProgressBar mProgressBar;
    private ScrollView scrollView;
    private RelativeLayout mToolbar;
    private String wishName;

    public AddWishFragment() {
        super();
        setArguments(new Bundle());
    }
    public interface OnWishCreated {
        void getWishlistFromAddWish(Wishlist wList);

    }

    AddWishFragment.OnWishCreated mOnWishCreated;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addwish, container, false);
        scrollView = view.findViewById(R.id.scrollView);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mNameInfo = (EditText) view.findViewById(R.id.name_Info);
        mWishPrice = (EditText) view.findViewById(R.id.wishPrice);
        mWebsiteTxt = (EditText) view.findViewById(R.id.websiteTxt);
        mDescriptionTxt = (EditText) view.findViewById(R.id.descriptionTxt);
        mCameraIcon = (ImageView) view.findViewById(R.id.cameraIcon);
        mWishImage = (ImageView) view.findViewById(R.id.wishImage);
        mIvCheckMark = (ImageView) view.findViewById(R.id.ivCheckMark);
        mIvBackArrow = (ImageView) view.findViewById(R.id.ivBackArrow);
        mRatingBar  = (RatingBar) view.findViewById(R.id.ratingBar);
        mRatingText = (TextView) view.findViewById(R.id.ratingText);
        mRatingText.setText("Meh...");
         mToolbar  = view.findViewById(R.id.relativeLayout1);
        mWishList = getWishListFromBundle();
        initProgressBar();
//        LayerDrawable stars = (LayerDrawable) mRatingBar.getProgressDrawable();
//        stars.getDrawable(2).setColorFilter(Color.parseColor("#ca0c05"), PorterDuff.Mode.SRC_ATOP);
        mWishImage.setVisibility(View.GONE);
        mCameraIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCameraIcon.setVisibility(View.GONE);
                openPhotoDialog();
            }
        });

        mWishImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openPhotoDialog();
            }
        });

        mIvCheckMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNewWish();
            }
        });

        mIvBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });
        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                setRating();
            }
        });
        return view;
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

    private void setRating() {
        rating = mRatingBar.getRating();
        mRatingText.setText(String.valueOf(rating));
        switch ((int) mRatingBar.getRating()) {
            case 1:
                mRatingText.setText("Meh...");
                break;
            case 2:
                mRatingText.setText("Good");
                break;
            case 3:
                mRatingText.setText("I like this");
                break;
            case 4:
                mRatingText.setText("I like this A LOT!");
                break;
            case 5:
                mRatingText.setText("Absolutely! It's my mission in life to have this");
                break;
            default:
                mRatingText.setText("");
        }
    }

    private void saveNewWish() {

        wishName = mNameInfo.getText().toString();
        if (!TextUtils.isEmpty(wishName)) {
            mNameInfo.setError(null);
            scrollView.setVisibility(View.GONE);
            mToolbar.setVisibility(View.GONE);
            showProgressBar();
            Log.d(TAG, "saveNewWish: Clicked on the checkMark");
            if(checkStringIfNull(mNameInfo.getText().toString())) {
                Log.d(TAG, "saveNewWish: Adding a new wish");
                DatabaseHelper databaseHelper = new DatabaseHelper();
                Wish wish = new Wish();
                wish.setName(mNameInfo.getText().toString());
                wish.setPrice(mWishPrice.getText().toString());
                wish.setLink(mWebsiteTxt.getText().toString());
                wish.setDescription(mDescriptionTxt.getText().toString());
                wish.setRating(rating);
                wish.setOwner(mWishList.getId());
                mWishImage.setDrawingCacheEnabled(true);
                mWishImage.buildDrawingCache();
                Bitmap bitmap = mWishImage.getDrawingCache();
                if(bitmap == null){
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.images);
                }

                databaseHelper.createWish(wish, bitmap, new ICallBackDatabase() {
                    @Override
                    public void onFinishWishList(Wishlist wList) {

                    }

                    @Override
                    public void onFinishWishListList(ArrayList list) {

                    }

                    @Override
                    public void onFinishWish(Wish wish) {
                        MainActivity.mSelectedImage = null;
                        mOnWishCreated.getWishlistFromAddWish(mWishList);
                    }

                    @Override
                    public void onFinnishGetWishes(ArrayList list) {

                    }

                });
            }


        }   else {
            mNameInfo.setError("Name is required!");
            scrollView.fullScroll(ScrollView.FOCUS_UP);
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("abc", "onResume: got image from bundle");
        if(MainActivity.mSelectedImage != null){
            mWishImage.setImageBitmap(MainActivity.mSelectedImage);
            Log.d("abc", "onResume: set image on the view ");
        }else {
            mCameraIcon.setVisibility(View.VISIBLE);
            mWishImage.setVisibility(View.GONE);
        }
        Log.d(TAG, "resumed");

    }



    private void openPhotoDialog() {
        Log.d(TAG, "openPhotoDialog: Opening dialog to choose a photo");
        mWishImage.setVisibility(View.VISIBLE);
        ChangePhotoDialog dialog = new ChangePhotoDialog();
        dialog.show(getFragmentManager(), getString(R.string.change_photo_dialog));
        dialog.setTargetFragment(AddWishFragment.this, 1);
    }

    public boolean checkStringIfNull(String string) {
        return !string.equals("");
    }
    private Wishlist getWishListFromBundle() {

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            return bundle.getParcelable("WishList");
        } else {
            return null;
        }

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnWishCreated = (AddWishFragment.OnWishCreated) getActivity();

        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage());

        }
    }

}
