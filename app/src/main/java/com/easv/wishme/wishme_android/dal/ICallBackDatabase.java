package com.easv.wishme.wishme_android.dal;

import com.easv.wishme.wishme_android.entities.Wishlist;

import java.util.ArrayList;

public interface ICallBackDatabase {
    void onFinishWishList(Wishlist wList);
    void onFinishWishListList(ArrayList list);

}
