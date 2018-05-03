package com.easv.wishme.wishme_android.entities;

public class User {

    String name, email, password, address, contactEmail;
    boolean image;

    public User(String name, String email, String password, String address, String contactEmail, boolean image) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.contactEmail = contactEmail;
        this.image = image;
    }
    public User() {

    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public boolean getImage() {
        return image;
    }

    public void setImage(boolean image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
