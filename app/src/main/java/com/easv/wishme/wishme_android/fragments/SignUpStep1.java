package com.easv.wishme.wishme_android.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.easv.wishme.wishme_android.R;

public class SignUpStep1 extends Fragment {

    private static final String TAG = "CreateUserFragment1";
    private EditText mEmailET, mPasswordET, mRepeatPasswordET;
    private Button mNext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup_step_1, container, false);

        mEmailET = (EditText) view.findViewById(R.id.emailET);
        mPasswordET = (EditText) view.findViewById(R.id.passwordET);
        mRepeatPasswordET = (EditText) view.findViewById(R.id.repeatPasswordET);
        mNext = (Button) view.findViewById(R.id.nextBtn);

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });

        return view;
    }

    private void next() {

    }


}
