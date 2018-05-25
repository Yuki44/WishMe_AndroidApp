package com.easv.wishme.wishme_android.interfaces;

import com.easv.wishme.wishme_android.entities.Wish;
import com.easv.wishme.wishme_android.entities.Wishlist;

import java.util.ArrayList;

public interface ICallBackDatabase {
    void onFinishWishList(Wishlist wList);

    void onFinishWishListList(ArrayList list);

    void onFinishWish(Wish wish);

    void onFinnishGetWishes(ArrayList list);
}
