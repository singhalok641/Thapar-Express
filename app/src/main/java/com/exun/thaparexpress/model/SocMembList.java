package com.exun.thaparexpress.model;

/**
 * Created by n00b on 1/13/2016.
 */
public class SocMembList {

    String name, desi, image , email;
    int phone, id;

    public SocMembList(){}

    public SocMembList(int id, String name, String desi, String image, String email, int phone){
        this.name = name;
        this.desi = desi;
        this.image = image;
        this.phone = phone;
        this.email = email;
        this.id = id;
    }

    public void setDesi(String desi) {
        this.desi = desi;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesi() {
        return desi;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public int getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
