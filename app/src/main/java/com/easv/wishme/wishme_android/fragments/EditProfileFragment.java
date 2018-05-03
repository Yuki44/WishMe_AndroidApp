package com.easv.wishme.wishme_android.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.easv.wishme.wishme_android.R;
import com.easv.wishme.wishme_android.dal.AuthenticationHelper;
import com.easv.wishme.wishme_android.entities.User;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileFragment extends Fragment {

    private static final String TAG = "EditProfileFragment";
    private User mUser;
    private EditText mNameET, mContactEmailET, mAddressET;
    private CircleImageView mImageView;
    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editprofile, container, false);
        mNameET = view.findViewById(R.id.nameET);
        mContactEmailET = view.findViewById(R.id.contactEmailET);
        mAddressET = view.findViewById(R.id.locationET);
        toolbar = view.findViewById(R.id.editProfileToolbar);

        ImageView ivBackArrow = view.findViewById(R.id.ivBackArrow);
        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked back arrow.");
                    goToHomeFragment();
            }
        });

        ImageView ivCheckMark = view.findViewById(R.id.ivCheckMark);
        ivCheckMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: saving the edited profile.");
                if (!mNameET.getText().toString().isEmpty()
                    && !mContactEmailET.getText().toString().isEmpty()
                    && !mAddressET.getText().toString().isEmpty()) {
                    Log.d(TAG, "onClick: saving edited User: " + mNameET.getText().toString());

                    //DatabaseHelper databaseHelper = new DatabaseHelper(EditFriendActivity.this);
                    //Cursor cursor = databaseHelper.getFriendID(mFriend);/getUserWithInfo()?
                    // if User found (!null)

                    //mFriend.setProfileImage(mSelectedImagePath);
                   /*
                        mFriend.setName(mName.getText().toString());
                        mFriend.setPhone(mPhoneNumber.getText().toString());
                        mFriend.setAddress(mAddressTxt.getText().toString());
                        mFriend.setBirthday(mBirthdayTxt.getText().toString());
                        mFriend.setEmail(mEmail.getText().toString());
                        mFriend.setWebsite(mWebsite.getText().toString());
                        mFriend.setLatitude(lat);
                        mFriend.setLongitude(lng);
                    }
                    if (databaseHelper.updateFriend(mFriend, friendID)) {
                        Toast.makeText(EditFriendActivity.this, "Contact Saved", Toast.LENGTH_SHORT).show();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        try {
                            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        } catch (NullPointerException e) {
                            Log.d(TAG, "setAppBarState: NullPointerException " + e.getMessage());
                        }
                        Intent i = new Intent(EditFriendActivity.this, FriendActivity.class);
                        i.putExtra("friendObj", mFriend);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(EditFriendActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EditFriendActivity.this, "Name, Phone Number and Address are necessary", Toast.LENGTH_LONG).show();
                }
                */
                    //temporary }
                }
                goToHomeFragment();
            }
        });

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        return view;
    }

    private void goToHomeFragment(){
        HomeFragment fragment = new HomeFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
