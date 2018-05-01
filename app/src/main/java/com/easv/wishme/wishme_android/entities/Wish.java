package com.easv.wishme.wishme_android.entities;

public class Wish {

    String wishName, price, link, description, image;
    int rating;

    public Wish(String wishName, String price, String link, String description, String image, int rating) {
        this.wishName = wishName;
        this.price = price;
        this.link = link;
        this.description = description;
        this.image = image;
        this.rating = rating;
    }

    public String getWishName() {
        return wishName;
    }

    public void setWishName(String wishName) {
        this.wishName = wishName;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Wish{" +
                "wishName='" + wishName + '\'' +
                ", price='" + price + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", rating=" + rating +
                '}';
    }
}
