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

import com.easv.wishme.wishme_android.R;
import com.easv.wishme.wishme_android.dal.DatabaseHelper;
import com.easv.wishme.wishme_android.utils.ChangePhotoDialog;

public class AddWishFragment extends Fragment {
    private static final String TAG = "AddWishFragment";
    private EditText mNameInfo, mWishPrice, mWebsiteTxt, mDescriptionTxt;
    private ImageView mCamera, mWishImage, mIvCheckMark;
    private int mRating;


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
        mWishImage = (ImageView) view.findViewById(R.id.ivCamera);
        mCamera = (ImageView) view.findViewById(R.id.wishImage);
        mIvCheckMark = (ImageView) view.findViewById(R.id.ivCheckMark);
        mWishImage.setVisibility(View.GONE);
        mCamera.setOnClickListener(new View.OnClickListener() {
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

        return view;
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
//            ImageView wishImage = mWishImage.
//
//            databaseHelper.addWish(wish);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Bitmap mSelectedImage = getBitmapFromBundle();
        Log.d("abc", "onResume: got image from bundle");
        if(mSelectedImage != null){
            mWishImage.setImageBitmap(mSelectedImage);
            Log.d("abc", "onResume: set image on the view ");
        }
        Log.d(TAG, "resumed");

    }

    private Bitmap getBitmapFromBundle(){
        Log.d("abc", "getBitmapFromBundle: arguments: " + getArguments());

        Bundle bundle = this.getArguments();
        if(bundle != null){
            return bundle.getParcelable("Image");
        } else {
            return null;
        }
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
