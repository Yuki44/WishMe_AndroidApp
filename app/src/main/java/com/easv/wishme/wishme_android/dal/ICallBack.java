package com.easv.wishme.wishme_android.dal;

import com.easv.wishme.wishme_android.entities.User;
import com.google.firebase.auth.FirebaseUser;

public interface ICallBack {

    void onFinish(User user);
    void onFinishFireBaseUser(FirebaseUser user);
}
