package com.easv.wishme.wishme_android.activities;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.easv.wishme.wishme_android.R;
import com.easv.wishme.wishme_android.dal.AuthenticationHelper;
import com.easv.wishme.wishme_android.dal.DatabaseHelper;
import com.easv.wishme.wishme_android.entities.User;
import com.easv.wishme.wishme_android.entities.Wish;
import com.easv.wishme.wishme_android.entities.Wishlist;
import com.easv.wishme.wishme_android.fragments.AddWishFragment;
import com.easv.wishme.wishme_android.fragments.HomeFragment;
import com.easv.wishme.wishme_android.fragments.LoginFragment;
import com.easv.wishme.wishme_android.fragments.SignUpStep1;
import com.easv.wishme.wishme_android.fragments.SignUpStep2;
import com.easv.wishme.wishme_android.fragments.WishDetailsFragment;
import com.easv.wishme.wishme_android.fragments.WishesFragment;
import com.easv.wishme.wishme_android.dialogfragments.EditWishlistDialog;
import com.easv.wishme.wishme_android.utils.UniversalImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MainActivity extends AppCompatActivity
        implements SignUpStep1.OnUserCreatedListener,
        HomeFragment.OnWishlistItemClicked,
        WishesFragment.OnEditWishList,
        WishesFragment.OnWishListToAddWishListener,
        WishesFragment.OnWishRetrievedListener,
        AddWishFragment.OnWishCreated {

    User user;
    AuthenticationHelper authHelper;
    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE = 1;
    public static Bitmap mSelectedImage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started.");
        authHelper = new AuthenticationHelper();
        initImageLoader();
        mSelectedImage = null;
        verifyPermissions();
    }

    private void init() {
        if (user == null) {
            LoginFragment fragment = new LoginFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
            return;
        }
        HomeFragment fragment = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void initImageLoader() {
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(MainActivity.this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

    private void verifyPermissions() {
        Log.d(TAG, "verifyPermissions: asking user for permissions");
        String[] permissions = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[0]) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[1]) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[2]) == PackageManager.PERMISSION_GRANTED) {
            init();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, permissions, REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        verifyPermissions();
    }



    @Override
    public void getUser(User user) {
        Log.d(TAG, "getUser: user received from SignupStep1");
        SignUpStep2 fragment = new SignUpStep2();
        Bundle args = new Bundle();
        args.putParcelable("UserSecurity", user);
        fragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void getWishlistItemClicked(Wishlist wList) {
        WishesFragment fragment = new WishesFragment();
        Bundle args = new Bundle();
        args.putParcelable("WishList", wList);
        fragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void getWishToDisplay(Wish wish) {
        WishDetailsFragment fragment = new WishDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable("WishDetails", wish);
        Log.d(TAG, "getWishToDisplay: MainActivity::::" + wish);
        fragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    @Override
    public void getWishListToAddWish(Wishlist wList) {
        AddWishFragment fragment = new AddWishFragment();
        Bundle args = new Bundle();
        args.putParcelable("WishList", wList);
        fragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void getWishlistFromAddWish(Wishlist wList) {
        WishesFragment fragment = new WishesFragment();
        Bundle args = new Bundle();
        args.putParcelable("WishList", wList);
        fragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    @Override
    public void getWishlist(Wishlist wList) {
        EditWishlistDialog dialog = new EditWishlistDialog();
        Bundle args = new Bundle();
        args.putParcelable("WishList", wList);
        dialog.setArguments(args);

        dialog.show(getSupportFragmentManager(), getString(R.string.edit));
    }

    public void clearBackStack(){
        FragmentManager fm = this.getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }
}

