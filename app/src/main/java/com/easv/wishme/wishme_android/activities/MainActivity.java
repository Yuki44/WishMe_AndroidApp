package com.easv.wishme.wishme_android.activities;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.easv.wishme.wishme_android.R;
import com.easv.wishme.wishme_android.entities.User;
import com.easv.wishme.wishme_android.fragments.HomeFragment;
import com.easv.wishme.wishme_android.utils.UniversalImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.easv.wishme.wishme_android.fragments.LoginFragment;

public class MainActivity extends AppCompatActivity {
    User user;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started.");
        initImageLoader();
        init();
    }


    private void init() {
        if(user == null){
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

}

