package com.easv.wishme.wishme_android.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
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
import com.easv.wishme.wishme_android.entities.User;
import com.easv.wishme.wishme_android.utils.ChangePhotoDialog;

public class SignUpStep1 extends Fragment {

    private static final String TAG = "CreateUserFragment1";
    private EditText mEmailET, mPasswordET, mRepeatPasswordET;
    private Button mNext;


    private Context mContext;
    private String email, password;
    private User mUser;

    public interface OnUserCreatedListener{
        void getUser(String email, String password);
    }
    OnUserCreatedListener mOnUserCreatedListener;

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
               email = mEmailET.getText().toString();
               password = mPasswordET.getText().toString();

               if (checkInputs(email,  password, mRepeatPasswordET.getText().toString())) {
                   if(doStringsMatch(password, mRepeatPasswordET.getText().toString())){
                       mOnUserCreatedListener.getUser(email, password);
                   }else{
                       Toast.makeText(getActivity(), "passwords do not match", Toast.LENGTH_SHORT).show();
                   }
               }else{
                   Toast.makeText(getActivity(), "All fields must be filled", Toast.LENGTH_SHORT).show();
               }

        SignUpStep2 fragment = new SignUpStep2();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    /**
     * Return true if @param 's1' matches @param 's2'
     * @param s1
     * @param s2
     * @return
     */
    private boolean doStringsMatch(String s1, String s2){
        return s1.equals(s2);
    }

    /**
     * Checks all the input fields for null
     * @param email
     * @param password
     * @return
     */
    private boolean checkInputs(String email, String password, String repeatPassword){
        Log.d(TAG, "checkInputs: checking inputs for null values");
        if(email.equals("") ||  password.equals("") || repeatPassword.equals("")){
            Toast.makeText(getActivity(), "All fields must be filled out", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    @Override
    public void onAttach(Context context) {
        try{
            mOnUserCreatedListener =  (OnUserCreatedListener) getActivity();
        }catch(ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage() );
        }
        super.onAttach(context);
    }


}
