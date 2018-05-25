package com.easv.wishme.wishme_android.interfaces;

import android.graphics.Bitmap;

import com.easv.wishme.wishme_android.entities.User;
import com.google.firebase.auth.FirebaseUser;

public interface ICallBack {

    void onFinish(User user);

    void onFinishFireBaseUser(FirebaseUser user);

    void onFinishGetImage(Bitmap bitmap);
}
