package com.example.chat.Adapter;

public class UserModul {

    String name;
    String email;
    String search;
    String phone;
    String cover;
    String image;
    String about;
    String uid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public UserModul() {
    }

    public UserModul(String name, String email, String search, String phone, String cover, String image, String about, String uid) {
        this.name = name;
        this.email = email;
        this.search = search;
        this.phone = phone;
        this.cover = cover;
        this.image = image;
        this.about = about;
        this.uid = uid;
    }
}
