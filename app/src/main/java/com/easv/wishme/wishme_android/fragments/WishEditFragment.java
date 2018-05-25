package com.easv.wishme.wishme_android.fragments;

import android.content.Context;
import android.graphics.Bitmap;
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

import com.easv.wishme.wishme_android.R;
import com.easv.wishme.wishme_android.activities.MainActivity;
import com.easv.wishme.wishme_android.dal.DatabaseHelper;
import com.easv.wishme.wishme_android.dialogfragments.ChangePhotoDialog;
import com.easv.wishme.wishme_android.entities.User;
import com.easv.wishme.wishme_android.entities.Wish;
import com.easv.wishme.wishme_android.entities.Wishlist;
import com.easv.wishme.wishme_android.interfaces.ICallBack;
import com.easv.wishme.wishme_android.interfaces.ICallBackDatabase;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class WishEditFragment extends Fragment {

    private static final String TAG = "EditWishFragment";
    private EditText mNameInfo, mWishPrice, mWebsiteTxt, mDescriptionTxt;
    private TextView mRatingText;
    private ImageView mWishImage, mIvCheckMark, mIvBackArrow;
    private RatingBar mRatingBar;
    private float rating;
    private ProgressBar mProgressBar, mImageProgressbar;
    private ScrollView scrollView;
    private RelativeLayout mToolbar;
    private Wish wishFromBundle;
    private DatabaseHelper databaseHelper;

    public WishEditFragment() {
        super();
        setArguments(new Bundle());
    }

    public interface UpdateWishDone {
        void getWishFromEditView(Wish wish);
    }

    WishEditFragment.UpdateWishDone mUpdateWishDone;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wish_edit, container, false);
        wishFromBundle = getWishFromBundle();
        scrollView = view.findViewById(R.id.scrollView);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mImageProgressbar = (ProgressBar) view.findViewById(R.id.imageProgressBar);
        mNameInfo = (EditText) view.findViewById(R.id.name_Info);
        mWishPrice = (EditText) view.findViewById(R.id.wishPrice);
        mWebsiteTxt = (EditText) view.findViewById(R.id.websiteTxt);
        mDescriptionTxt = (EditText) view.findViewById(R.id.descriptionTxt);
        mWishImage = (ImageView) view.findViewById(R.id.wishImage);
        mIvCheckMark = (ImageView) view.findViewById(R.id.ivCheckMark);
        mRatingBar = (RatingBar) view.findViewById(R.id.ratingBar);
        mRatingText = (TextView) view.findViewById(R.id.ratingText);
        mIvBackArrow = view.findViewById(R.id.ivBackArrow);
        mToolbar = view.findViewById(R.id.relLayout1);
        initProgressBar();
        initImageProgressBar();
        databaseHelper = new DatabaseHelper();
        mIvBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
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
                editWish(wishFromBundle);

            }

        });
        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                setRating();
            }
        });

        setWishToEdit();
        return view;
    }

    private void editWish(final Wish wish) {
        if (!TextUtils.isEmpty(mNameInfo.getText())) {
            scrollView.setVisibility(View.GONE);
            mToolbar.setVisibility(View.GONE);
            showProgressBar();
            wish.setName(mNameInfo.getText().toString());
            wish.setPrice(mWishPrice.getText().toString());
            wish.setLink(mWebsiteTxt.getText().toString());
            wish.setDescription(mDescriptionTxt.getText().toString());
            if (mRatingBar.getRating() == 0) {
                wish.setRating(1);
            } else {
                wish.setRating(mRatingBar.getRating());
            }
            databaseHelper.editWish(wish, new ICallBackDatabase() {
                @Override
                public void onFinishWishList(Wishlist wList) {
                }

                @Override
                public void onFinishWishListList(ArrayList list) {
                }

                @Override
                public void onFinishWish(final Wish wish) {
                    if (MainActivity.mSelectedImage == null) {
                        hideProgressBar();
                        FragmentManager fm = getFragmentManager();
                        fm.popBackStack();
                    }
                }

                @Override
                public void onFinnishGetWishes(ArrayList list) {
                }
            });

            if (MainActivity.mSelectedImage != null) {
                databaseHelper.createWishImage(MainActivity.mSelectedImage, wish.getId(), new ICallBack() {
                    @Override
                    public void onFinish(User user) {
                    }

                    @Override
                    public void onFinishFireBaseUser(FirebaseUser user) {
                    }

                    @Override
                    public void onFinishGetImage(Bitmap bitmap) {
                        MainActivity.mSelectedImage = null;
                        FragmentManager fm = getFragmentManager();
                        fm.popBackStack();
                    }
                });
            }
        } else {
            mNameInfo.setError("A name is required");
        }
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

    private void showImageProgressBar() {
        mImageProgressbar.setVisibility(View.VISIBLE);
    }

    private void hideImageProgressBar() {
        mImageProgressbar.setVisibility(View.GONE);
    }

    private void initImageProgressBar() {

        mImageProgressbar.setVisibility(View.INVISIBLE);
    }

    private void setWishToEdit() {
        showImageProgressBar();
        mNameInfo.setText(wishFromBundle.getName());
        mWishPrice.setText(wishFromBundle.getPrice());
        mWebsiteTxt.setText(wishFromBundle.getLink());
        mDescriptionTxt.setText(wishFromBundle.getDescription());
        mRatingBar.setRating(wishFromBundle.getRating());
        databaseHelper.getWishImage(wishFromBundle.getId(), new ICallBack() {
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
            }
        });
    }

    private void openPhotoDialog() {
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
                mRatingText.setText("I like this a lot!");
                break;
            case 5:
                mRatingText.setText("Absolutely! It's my mission in life to have this");
                break;
            default:
                mRatingText.setText("");
        }
    }

    private Wish getWishFromBundle() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            return bundle.getParcelable("WishDetails");
        } else {
            return null;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mUpdateWishDone = (WishEditFragment.UpdateWishDone) getActivity();
        } catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MainActivity.mSelectedImage != null) {
            mWishImage.setImageBitmap(MainActivity.mSelectedImage);
        }
    }
}
