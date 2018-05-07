package com.easv.wishme.wishme_android.entities;

public class Wishlist {

    String wListName, owner;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }


    public Wishlist() {
    }

    public Wishlist(String wListName, String owner) {
        this.wListName = wListName;
        this.owner = owner;
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
