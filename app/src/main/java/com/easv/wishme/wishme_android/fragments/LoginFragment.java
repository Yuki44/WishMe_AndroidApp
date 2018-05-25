package com.easv.wishme.wishme_android.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.easv.wishme.wishme_android.R;
import com.easv.wishme.wishme_android.dal.AuthenticationHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {

    private static final String TAG = "loginFragment";
    private EditText mEmailET, mPasswordET;
    private Button mLoginBtn, mSignUpBtn;
    private AuthenticationHelper authHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        authHelper = new AuthenticationHelper();
        mEmailET = view.findViewById(R.id.emailET);
        mPasswordET = view.findViewById(R.id.passwordET);
        mLoginBtn = view.findViewById(R.id.loginBtn);
        mSignUpBtn = view.findViewById(R.id.signUpBtn);
        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(mEmailET.getText().toString(), mPasswordET.getText().toString());
            }
        });
        return view;
    }

    private void signUp() {
        SignUpStep1 fragment = new SignUpStep1();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = authHelper.getmAuth().getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            HomeFragment fragment = new HomeFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
        } else {
            Log.d(TAG, "UpdateUi user null");
        }
    }

    private void login(String email, String password) {
        email = mEmailET.getText().toString();
        password = mPasswordET.getText().toString();
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            mEmailET.setError(null);
            mPasswordET.setError(null);
            if (email.contains("@") && email.contains(".")) {
                if (password.toCharArray().length >= 6) {
                    authHelper.getmAuth().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = authHelper.getmAuth().getCurrentUser();
                                updateUI(user);
                            } else {
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                updateUI(null);
                            }
                        }
                    });
                } else {
                    mPasswordET.setError("Password must be at least 6 characters long");
                }
            } else {
                mEmailET.setError("A valid email is required");
            }
        } else {
            if (TextUtils.isEmpty(email)) {
                mEmailET.setError("Email missing");
            } else if (TextUtils.isEmpty(password)) {
                mPasswordET.setError("Password missing");
            }
        }
    }
}
