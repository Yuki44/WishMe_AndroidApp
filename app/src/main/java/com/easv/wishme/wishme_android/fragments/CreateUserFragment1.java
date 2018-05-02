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

public class CreateUserFragment1 extends Fragment {

    private static final String TAG = "CreateUserFragment1";
    private EditText emailET;
    private EditText passwordET;
    private EditText repeatPasswordET;
    private Button next;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_user_1, container, false);

        emailET = view.findViewById(R.id.nameET);
        passwordET = view.findViewById(R.id.contactEmailET);
        repeatPasswordET = view.findViewById(R.id.repeatPasswordET);
        next = view.findViewById(R.id.nextBtn);

        next.setOnClickListener(new View.OnClickListener() {
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
