package com.easv.wishme.wishme_android.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.easv.wishme.wishme_android.R;
import com.easv.wishme.wishme_android.dal.AuthenticationHelper;
import com.easv.wishme.wishme_android.dal.ICallBack;
import com.easv.wishme.wishme_android.entities.User;
import com.easv.wishme.wishme_android.utils.ChangePhotoDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.PrivateKey;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpStep2 extends Fragment {

    private static final String TAG = "CreateUserFragment2";
    private CircleImageView profileImage;
    private User mUser;
    private Button signUpBtn;
    private EditText nameET;
    private EditText contactEmailET;
    private AuthenticationHelper authHelper;

    private EditText addressET;


    //Firebase
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

        if(mUser != null){
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
                HomeFragment fragment = new HomeFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();

            }
        });



    }

    private void changePic() {
        Log.d(TAG, "onClick: opening dialog to choose new photo");
        ChangePhotoDialog dialog = new ChangePhotoDialog();
        dialog.show(getFragmentManager(), getString(R.string.change_photo_dialog));
        dialog.setTargetFragment(SignUpStep2.this, 1);
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

private User getUserFromBundle(){
    Log.d(TAG, "getUserFromBundle: arguments: " + getArguments());

    Bundle bundle = this.getArguments();
    if(bundle != null){
        return bundle.getParcelable("UserSecurity");
    } else {
        return null;
    }
}

}
