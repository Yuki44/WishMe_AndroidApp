package com.easv.wishme.wishme_android.entities;

public class Wishlist {

    String wListName;

    public Wishlist() {

    }

    public Wishlist(String wListName) {
        this.wListName = wListName;
    }

    public String getwListName() {
        return wListName;
    }

    public void setwListName(String wListName) {
        this.wListName = wListName;
    }

    @Override
    public String toString() {
        return "Wishlist{" +
                "wListName='" + wListName + '\'' +
                '}';
    }
}
