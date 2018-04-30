package com.easv.wishme.wishme_android.entities;

public class Wishlist {

    String wListName;
    Wish[] wList;

    public Wishlist(String wListName, Wish[] wList) {
        this.wListName = wListName;
        this.wList = wList;
    }

    public String getwListName() {
        return wListName;
    }

    public void setwListName(String wListName) {
        this.wListName = wListName;
    }

    public Wish[] getwList() {
        return wList;
    }

    public void setwList(Wish[] wList) {
        this.wList = wList;
    }

}
