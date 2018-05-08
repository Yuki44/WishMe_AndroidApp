package com.easv.wishme.wishme_android.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.easv.wishme.wishme_android.R;
import com.easv.wishme.wishme_android.activities.MainActivity;
import com.easv.wishme.wishme_android.dal.DatabaseHelper;
import com.easv.wishme.wishme_android.dialogfragments.ChangePhotoDialog;

public class AddWishFragment extends Fragment {
    private static final String TAG = "AddWishFragment";
    private EditText mNameInfo, mWishPrice, mWebsiteTxt, mDescriptionTxt;
    private TextView mRatingText;
    private ImageView mCameraIcon, mWishImage, mIvCheckMark;
    private RatingBar mRatingBar;
    private int rating;

    public AddWishFragment() {
        super();
        setArguments(new Bundle());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addwish, container, false);

        mNameInfo = (EditText) view.findViewById(R.id.name_Info);
        mWishPrice = (EditText) view.findViewById(R.id.wish_price);
        mWebsiteTxt = (EditText) view.findViewById(R.id.websiteTxt);
        mDescriptionTxt = (EditText) view.findViewById(R.id.descriptionTxt);
        mCameraIcon = (ImageView) view.findViewById(R.id.cameraIcon);
        mWishImage = (ImageView) view.findViewById(R.id.wishImage);
        mIvCheckMark = (ImageView) view.findViewById(R.id.ivCheckMark);
        mRatingBar  = (RatingBar) view.findViewById(R.id.ratingBar);
        mRatingText = (TextView) view.findViewById(R.id.ratingText);
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
        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                setRating();
            }
        });
        return view;
    }

    private void setRating() {
        rating = (int)mRatingBar.getRating();
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
        Log.d(TAG, "saveNewWish: Clicked on the checkMark");
        if(checkStringIfNull(mNameInfo.getText().toString())){
            Log.d(TAG, "saveNewWish: Adding a new wish");
            DatabaseHelper databaseHelper = new DatabaseHelper();
          String wishName = mNameInfo.getText().toString();
            String wishPrice = mWishPrice.getText().toString();
            String websiteLink = mWebsiteTxt.getText().toString();
            String wishDescription = mDescriptionTxt.getText().toString();
             int finalRating = rating;
            Bitmap wishImage = MainActivity.mSelectedImage;



//            databaseHelper.createWish(wish);
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

}
