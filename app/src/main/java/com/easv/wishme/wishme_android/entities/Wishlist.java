package com.easv.wishme.wishme_android.entities;

import java.util.List;

public class Wishlist {

    String wListName;
    List<Wish> wList;

    public Wishlist(String wListName, List<Wish> wList) {
        this.wListName = wListName;
        this.wList = wList;
    }

    public String getwListName() {
        return wListName;
    }

    public void setwListName(String wListName) {
        this.wListName = wListName;
    }

    public List<Wish> getwList() {
        return wList;
    }

    public void setwList(List<Wish> wList) {
        this.wList = wList;
    }
}
