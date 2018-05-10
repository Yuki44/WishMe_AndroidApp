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
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.easv.wishme.wishme_android.R;
import com.easv.wishme.wishme_android.dialogfragments.ChangePhotoDialog;
import com.easv.wishme.wishme_android.entities.Wish;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class WishDetailsFragment extends Fragment {

    private static final String TAG = "WishDetailsFragment";
    private Wish wishToDisplay;
    private TextView mWishName, mWishPrice, mWishLink, mWishDescription;
    private RatingBar ratingBar;
    private ImageView mImageView;
    public static Bitmap mSelectedImage;
    private Toolbar toolbar;
    private ProgressBar mProgressBar;
    private ViewSwitcher mDescriptionSwitcher;
    public WishDetailsFragment() {
        super();
        setArguments(new Bundle());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wish_detail, container, false);
        wishToDisplay = getWishFromBundle();
        mWishName = view.findViewById(R.id.wishNameTV);
        mWishPrice = view.findViewById(R.id.wishPriceTV);
        mWishLink = view.findViewById(R.id.wishLinkTV);
        mWishDescription = view.findViewById(R.id.wishDescriptionTV);
        mWishDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wishDescriptionClicked();
            }
        });
        mDescriptionSwitcher = view.findViewById(R.id.my_switcher);

        ratingBar = view.findViewById(R.id.wishRatingBar);
        mImageView = view.findViewById(R.id.wishImage);
        // mImageView.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                changePic();
        //            }
        //        });
        toolbar = view.findViewById(R.id.wishToolbar);



        ImageView ivBackArrow = view.findViewById(R.id.ivBackArrow);
        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked back arrow.");
                goToWishListFragment();
            }
        });

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        setWishInfo(wishToDisplay);

        return view;
    }

    private void goToWishListFragment() {
        getFragmentManager().popBackStack();
        Log.d(TAG, "goToWishListFragment: goin bakk");
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

    private void setWishInfo(Wish wish) {
                mWishName.setText(wish.getName());
                mWishPrice.setText(wish.getPrice());
                mWishLink.setText(wish.getLink());
                mWishDescription.setText(wish.getDescription());
                ratingBar.setRating(wish.getRating());
               // mImageView.setImageBitmap();
    }

    public void wishDescriptionClicked(){
        mDescriptionSwitcher.showNext(); //or switcher.showPrevious();
        TextView TV = (TextView) mDescriptionSwitcher.findViewById(R.id.wishDescriptionTV);
        TV.setText("changed sthhhg");
    }
}
