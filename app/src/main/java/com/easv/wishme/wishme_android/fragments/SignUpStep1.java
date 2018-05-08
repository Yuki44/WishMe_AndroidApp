package com.easv.wishme.wishme_android.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.easv.wishme.wishme_android.R;
import com.easv.wishme.wishme_android.entities.User;

public class SignUpStep1 extends Fragment {

    private static final String TAG = "CreateUserFragment1";
    private EditText mEmailET, mPasswordET, mRepeatPasswordET;
    private Button mNext;


    private String email, password;

    // From here
    public interface OnUserCreatedListener{
        void getUser(User user);
    }
    OnUserCreatedListener mOnUserCreatedListener;
// to here
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
                       User user = new User(null, email, password, null, null, false);
                       //from here
                       mOnUserCreatedListener.getUser(user);
                       //to here
                   }else{
                       Toast.makeText(getActivity(), "passwords do not match", Toast.LENGTH_SHORT).show();
                   }
               }else{
                   Toast.makeText(getActivity(), "All fields must be filled", Toast.LENGTH_SHORT).show();
               }


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
    //from here
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mOnUserCreatedListener = (OnUserCreatedListener) getActivity();
        }catch(ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage() );
        }
    }
//to here

}
