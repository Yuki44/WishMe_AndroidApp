package com.easv.wishme.wishme_android.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.easv.wishme.wishme_android.R;
import com.easv.wishme.wishme_android.dal.AuthenticationHelper;
import com.easv.wishme.wishme_android.dal.ICallBack;
import com.easv.wishme.wishme_android.entities.User;
import com.easv.wishme.wishme_android.utils.LogOutDialog;
import com.easv.wishme.wishme_android.utils.UniversalImageLoader;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {

    private static final String TAG = "homeFragment";
    private CardView mWishlistCard;
    private CardView mProfileCard;
    private Toolbar toolbar;
    private AuthenticationHelper authHelper;
    private TextView mNameTV;
    private TextView mAddressTV;
    private TextView mContactTV;
    private CircleImageView mImageView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        toolbar = view.findViewById(R.id.logoutToolbar);

        authHelper = new AuthenticationHelper();
        mWishlistCard = view.findViewById(R.id.cardView2);
        mAddressTV = view.findViewById(R.id.addressTV);
        mNameTV = view.findViewById(R.id.nameTV);
        mContactTV = view.findViewById(R.id.contactTV);
        mImageView = view.findViewById(R.id.profileImager);
        mProfileCard = view.findViewById(R.id.cardView);
        mProfileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();
            }
        });
        mWishlistCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishlistClicked();
            }
        });
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        setUserInfo();
        return view;
    }


    private void wishlistClicked() {
        WishesFragment fragment = new WishesFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuitem_logout:
                logout();
                return true;
            case R.id.menuitem_edit_user:
                editProfile();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void editProfile() {
        EditProfileFragment fragment = new EditProfileFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        return;
    }

    private void logout() {
        LogOutDialog dialog = new LogOutDialog();
        dialog.show(getFragmentManager(), getString(R.string.change_photo_dialog));
        dialog.setTargetFragment(HomeFragment.this, 1);




    }
    private void setUserInfo(){
      User user =  authHelper.getUserWithInfo(new ICallBack() {
                 @Override
                 public void onFinish(User user) {
                     mNameTV.setText(user.getname());
                     mContactTV.setText(user.getContactEmail());
                     mAddressTV.setText(user.getAddress());
                     if(user.getImage() == false){
                  UniversalImageLoader.setImage("", mImageView, null, "drawable://" + R.drawable.ic_no_profile_img);

              }
              Log.d(TAG, "setUserInfo: " + user.toString());

          }

          @Override
          public void onFinishFireBaseUser(FirebaseUser user) {

          }
      });



    }

}
