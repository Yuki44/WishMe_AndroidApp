package com.easv.wishme.wishme_android.entities;

public class Wishlist {

    String wListName;

    public Wishlist(String wListName) {
        this.wListName = wListName;
    }

    public String getwListName() {
        return wListName;
    }

    public void setwListName(String wListName) {
        this.wListName = wListName;
    }

}
