package com.exun.thaparexpress.model;

/**
 * Created by root on 11/15/16.
 */
public class MessMenuList {
    private String day, menu, time;
    private int id;

    public MessMenuList(){
    }

    public MessMenuList(String title, String thumbnailUrl,int id, String desc){
        this.day = day;
        this.menu = menu;
        this.id = id;
        this.time = time;
    }

    public String getMessMenuDay() {
        return day;
    }

    public void setMessMenuDay(String day) {
        this.day = day;
    }



    }
