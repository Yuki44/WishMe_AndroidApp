package com.easv.wishme.wishme_android.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class User implements Parcelable {

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

    protected User(Parcel in) {
        name = in.readString();
        email = in.readString();
        password = in.readString();
        address = in.readString();
        contactEmail = in.readString();
        image = in.readByte() != 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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
        return "User{" + "name='" + name + '\'' + ", email='" + email + '\'' + ", password='" + password + '\'' + ", address='" + address + '\'' + ", contactEmail='" + contactEmail + '\'' + ", image='" + image + '\'' + '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(address);
        dest.writeString(contactEmail);
        dest.writeByte((byte) (image ? 1 : 0));
    }

    public User(Map<String, Object> map) {
        name = (String) map.get("name");
        email = (String) map.get("email");
        password = (String) map.get("password");
        address = (String) map.get("address");
        contactEmail = (String) map.get("contactEmail");
    }

    public Map<String, Object> toMap() {
        Map<String, Object> res = new HashMap<>();
        res.put("name", getname());
        res.put("email", getEmail());
        res.put("password", getPassword());
        res.put("address", getAddress());
        res.put("contactEmail", getContactEmail());
        return res;
    }
}
