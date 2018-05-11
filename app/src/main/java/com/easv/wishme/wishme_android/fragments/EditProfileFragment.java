package com.easv.wishme.wishme_android.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.widget.RelativeLayout;

import com.easv.wishme.wishme_android.R;
import com.easv.wishme.wishme_android.activities.MainActivity;
import com.easv.wishme.wishme_android.dal.AuthenticationHelper;
import com.easv.wishme.wishme_android.interfaces.ICallBack;
import com.easv.wishme.wishme_android.entities.User;
import com.easv.wishme.wishme_android.dialogfragments.ChangePhotoDialog;
import com.google.firebase.auth.FirebaseUser;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileFragment extends Fragment {

    private static final String TAG = "EditProfileFragment";
    private EditText mNameET, mContactEmailET, mAddressET;
    private CircleImageView mImageView;
    private Toolbar toolbar;
    private AuthenticationHelper authHelper;
    private ProgressBar mProfileProgressBar;

    private RelativeLayout mRelativeLayout2;
    private ProgressBar mProgressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editprofile, container, false);
        mNameET = view.findViewById(R.id.nameET);
        mContactEmailET = view.findViewById(R.id.contactEmailET);
        mAddressET = view.findViewById(R.id.locationET);
        mImageView = view.findViewById(R.id.profileImage);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePic();
            }
        });
        mRelativeLayout2 = view.findViewById(R.id.relativeLayout2);
        mProgressBar = view.findViewById(R.id.progressBar);
        mProfileProgressBar = (ProgressBar) view.findViewById(R.id.profileImageProgressBar);
        toolbar = view.findViewById(R.id.editProfileToolbar);
        authHelper = new AuthenticationHelper();
        initImageProgressBar();

        ImageView ivBackArrow = view.findViewById(R.id.ivBackArrow);
        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked back arrow.");
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });

        ImageView ivCheckMark = view.findViewById(R.id.ivCheckMark);
        ivCheckMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: saving the edited profile.");
                if (!mNameET.getText().toString().isEmpty() && !mContactEmailET.getText().toString().isEmpty() && !mAddressET.getText().toString().isEmpty()) {
                    Log.d(TAG, "onClick: saving edited User: " + mNameET.getText().toString());
                    updateUser();
                }
            }
        });
        initProgressBar();
        setUserInfo();
        setProfileImage();
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        return view;
    }

    private void changePic() {
        Log.d(TAG, "onClick: opening dialog to choose new photo");
        ChangePhotoDialog dialog = new ChangePhotoDialog();
        dialog.show(getFragmentManager(), getString(R.string.change_photo_dialog));
        dialog.setTargetFragment(EditProfileFragment.this, 1);
    }

    private void goToHomeFragment() {
        HomeFragment fragment = new HomeFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }




    private void setProfileImage(){
        showImageProgressBar();
        MainActivity.mSelectedImage = authHelper.getProfileImage(new ICallBack() {
            @Override
            public void onFinish(User user) {}

            @Override
            public void onFinishFireBaseUser(FirebaseUser user) {}

            @Override
            public void onFinishGetImage(Bitmap bitmap) {
                hideImageProgressBar();
                mImageView.setImageBitmap(bitmap);
            }
        });
    }
    private void showProgressBar(){ mProgressBar.setVisibility(View.VISIBLE); }

    private void hideProgressBar(){
        mProgressBar.setVisibility(View.GONE);
    }

    private void initProgressBar(){ mProgressBar.setVisibility(View.INVISIBLE); }

    private void updateUser() {
       mRelativeLayout2.setVisibility(View.GONE);
        showProgressBar();
        authHelper.getUserWithInfo(new ICallBack() {
            @Override
            public void onFinish(User user) {
                user.setname(mNameET.getText().toString());
                user.setContactEmail(mContactEmailET.getText().toString());
                user.setAddress(mAddressET.getText().toString());
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
                        MainActivity.mSelectedImage = null;
                        goToHomeFragment();
                    }
                });
                Log.d(TAG, "setUserInfo: " + user.toString());
            }

            @Override
            public void onFinishFireBaseUser(FirebaseUser user) {}

            @Override
            public void onFinishGetImage(Bitmap bitmap) {}
        });
    }

    private void setUserInfo(){
        User user =  authHelper.getUserWithInfo(new ICallBack() {
            @Override
            public void onFinish(User user) {
                mNameET.setText(user.getname());
                mContactEmailET.setText(user.getContactEmail());
                mAddressET.setText(user.getAddress());
            }

            @Override
            public void onFinishFireBaseUser(FirebaseUser user) { }

            @Override
            public void onFinishGetImage(Bitmap bitmap) { }
        });
    }

    private void setNewProfileImage(){
       mImageView.setImageBitmap(MainActivity.mSelectedImage);

    }

    private void showImageProgressBar(){
        mProfileProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideImageProgressBar() {
        mProfileProgressBar.setVisibility(View.GONE);
    }

    private void initImageProgressBar() {

        mProfileProgressBar.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        if(MainActivity.mSelectedImage != null){
            mImageView.setImageBitmap(MainActivity.mSelectedImage);
            Log.d("abc", "onResume: set image on the view ");
        }
        Log.d(TAG, "resumed");
    }
}

