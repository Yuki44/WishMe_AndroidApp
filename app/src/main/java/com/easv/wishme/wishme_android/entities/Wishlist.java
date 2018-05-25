package com.easv.wishme.wishme_android.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Wishlist implements Parcelable {

    String wListName, owner, id;

    protected Wishlist(Parcel in) {
        wListName = in.readString();
        owner = in.readString();
        id = in.readString();
    }

    public static final Creator<Wishlist> CREATOR = new Creator<Wishlist>() {
        @Override
        public Wishlist createFromParcel(Parcel in) {
            return new Wishlist(in);
        }

        @Override
        public Wishlist[] newArray(int size) {
            return new Wishlist[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
        return "Wishlist{" + "wListName='" + wListName + '\'' + '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(wListName);
        dest.writeString(owner);
        dest.writeString(id);
    }
}
