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
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.easv.wishme.wishme_android.R;
import com.easv.wishme.wishme_android.dialogfragments.ChangePhotoDialog;
import com.easv.wishme.wishme_android.entities.Wish;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class WishEditFragment extends Fragment {

    private static final String TAG = "EditWishFragment";
    private EditText mNameInfo, mWishPrice, mWebsiteTxt, mDescriptionTxt;
    private TextView mRatingText;
    private ImageView mCameraIcon, mWishImage, mIvCheckMark;
    private RatingBar mRatingBar;
    private float rating;
    private ProgressBar mProgressBar;
    private ScrollView scrollView;
    private RelativeLayout mToolbar;
    private Wish wishFromBundle;

    public WishEditFragment() {
        super();
        setArguments(new Bundle());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wish_edit, container, false);
        wishFromBundle = getWishFromBundle();
        scrollView = view.findViewById(R.id.scrollView);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mNameInfo = (EditText) view.findViewById(R.id.name_Info);
        mWishPrice = (EditText) view.findViewById(R.id.wishPrice);
        mWebsiteTxt = (EditText) view.findViewById(R.id.websiteTxt);
        mDescriptionTxt = (EditText) view.findViewById(R.id.descriptionTxt);
        mWishImage = (ImageView) view.findViewById(R.id.wishImage);
        mIvCheckMark = (ImageView) view.findViewById(R.id.ivCheckMark);
        mRatingBar  = (RatingBar) view.findViewById(R.id.ratingBar);
        mRatingText = (TextView) view.findViewById(R.id.ratingText);
        mToolbar  = view.findViewById(R.id.relativeLayout1);


        mWishImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openPhotoDialog();
            }
        });
        mIvCheckMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Updated wish method", Toast.LENGTH_SHORT).show();
            }

        });
        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                setRating();
            }
        });

        setWishToEdit();


        return view;
    }

    private void setWishToEdit() {
        mNameInfo.setText(wishFromBundle.getName());
        mWishPrice.setText(wishFromBundle.getPrice());
        mWebsiteTxt.setText(wishFromBundle.getLink());
        mDescriptionTxt.setText(wishFromBundle.getDescription());
        mRatingBar.setRating(wishFromBundle.getRating());
        mWishImage.setImageBitmap(wishFromBundle.getImageBitmap());
    }

    private void openPhotoDialog() {
        Log.d(TAG, "openPhotoDialog: Opening dialog to choose a photo");
        mWishImage.setVisibility(View.VISIBLE);
        ChangePhotoDialog dialog = new ChangePhotoDialog();
        dialog.show(getFragmentManager(), getString(R.string.change_photo_dialog));
        dialog.setTargetFragment(WishEditFragment.this, 1);
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

    private Wish getWishFromBundle(){
        Bundle bundle = this.getArguments();
        if(bundle != null){
            return bundle.getParcelable("WishDetails");
        } else {
            return null;
        }
    }

}
