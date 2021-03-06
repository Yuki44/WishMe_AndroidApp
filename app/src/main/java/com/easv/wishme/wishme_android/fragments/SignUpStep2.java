package com.easv.wishme.wishme_android.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.easv.wishme.wishme_android.R;
import com.easv.wishme.wishme_android.activities.MainActivity;
import com.easv.wishme.wishme_android.dal.AuthenticationHelper;
import com.easv.wishme.wishme_android.interfaces.ICallBack;
import com.easv.wishme.wishme_android.entities.User;
import com.easv.wishme.wishme_android.dialogfragments.ChangePhotoDialog;
import com.easv.wishme.wishme_android.utils.UniversalImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpStep2 extends Fragment {

    private static final String TAG = "CreateUserFragment2";
    private CircleImageView profileImage;
    private User mUser;
    private Button signUpBtn;
    private EditText nameET;
    private EditText contactEmailET;
    private AuthenticationHelper authHelper;
    private RelativeLayout mRelativeLayout2;
    private EditText addressET;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressBar mProgressBar;

    public SignUpStep2() {
        super();
        setArguments(new Bundle());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup_step_2, container, false);
        authHelper = new AuthenticationHelper();
        contactEmailET = view.findViewById(R.id.contactEmailET);
        addressET = view.findViewById(R.id.addressET);
        nameET = view.findViewById(R.id.nameET);
        profileImage = (CircleImageView) view.findViewById(R.id.profileImage);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mUser = getUserFromBundle();
        signUpBtn = view.findViewById(R.id.nextBtn);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
        mRelativeLayout2 = view.findViewById(R.id.relativeLayout2);
        UniversalImageLoader.setImage("", profileImage, null, "drawable://" + R.drawable.ic_no_profile_img);

        if (mUser != null) {
            Log.d(TAG, "onCreateView: received User: " + mUser.getEmail() + " " + mUser.getPassword());
        }

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePic();
            }
        });
        initProgressBar();
        return view;
    }

    private void signUp() {
        if (!TextUtils.isEmpty(nameET.getText())) {
            mRelativeLayout2.setVisibility(mRelativeLayout2.INVISIBLE);
            showProgressBar();
            authHelper.signUpNewUser(mUser, new ICallBack() {
                @Override
                public void onFinish(User user) {
                }

                @Override
                public void onFinishFireBaseUser(FirebaseUser user) {
                    mUser.setAddress(addressET.getText().toString());
                    mUser.setname(nameET.getText().toString());
                    mUser.setContactEmail(contactEmailET.getText().toString());
                    authHelper.createUserProfile(mUser);
                    profileImage.setDrawingCacheEnabled(true);
                    profileImage.buildDrawingCache();
                    Bitmap bitmap = profileImage.getDrawingCache();

                    authHelper.createProfileImage(bitmap, new ICallBack() {
                        @Override
                        public void onFinish(User user) {
                        }

                        @Override
                        public void onFinishFireBaseUser(FirebaseUser user) {
                        }

                        @Override
                        public void onFinishGetImage(Bitmap bitmap) {
                            MainActivity.mSelectedImage = null;
                            loadHomeFragment();
                        }
                    });
                }

                @Override
                public void onFinishGetImage(Bitmap bitmap) {
                }
            });
        } else {
            nameET.setError("Your name is required");
        }
    }

    private void changePic() {
        ChangePhotoDialog dialog = new ChangePhotoDialog();
        dialog.show(getFragmentManager(), getString(R.string.change_photo_dialog));
        dialog.setTargetFragment(SignUpStep2.this, 1);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MainActivity.mSelectedImage != null) {
            profileImage.setImageBitmap(MainActivity.mSelectedImage);
        }
        Log.d(TAG, "resumed");
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

    private User getUserFromBundle() {
        Log.d("abc", "getUserFromBundle: arguments: " + getArguments());
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            return bundle.getParcelable("UserSecurity");
        } else {
            return null;
        }
    }

    private void loadHomeFragment() {
        HomeFragment fragment = new HomeFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
