package com.easv.wishme.wishme_android.entities;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Wish implements Parcelable {

    private String name, price, link, description, image, owner, id;
    private float rating;
    private Bitmap imageBitmap;

    public Wish() {
    }

    public Wish(String name, String price, String link, String description, String image, String owner, float rating, String id) {
        this.name = name;
        this.price = price;
        this.link = link;
        this.description = description;
        this.image = image;
        this.owner = owner;
        this.rating = rating;
        this.id = id;
    }

    protected Wish(Parcel in) {
        name = in.readString();
        price = in.readString();
        link = in.readString();
        description = in.readString();
        image = in.readString();
        owner = in.readString();
        rating = in.readFloat();
        id = in.readString();
    }

    public static final Creator<Wish> CREATOR = new Creator<Wish>() {
        @Override
        public Wish createFromParcel(Parcel in) {
            return new Wish(in);
        }

        @Override
        public Wish[] newArray(int size) {
            return new Wish[size];
        }
    };

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Wish{" + "name='" + name + '\'' + ", price='" + price + '\'' + ", link='" + link + '\'' + ", description='" + description + '\'' + ", image='" + image + '\'' + ", owner='" + owner + '\'' + ", rating='" + rating + '\'' + ", id='" + id + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(price);
        dest.writeString(link);
        dest.writeString(description);
        dest.writeString(image);
        dest.writeString(owner);
        dest.writeFloat(rating);
        dest.writeString(id);
    }
}
