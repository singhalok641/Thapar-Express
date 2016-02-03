package com.exun.thaparexpress.model;

/**
 * Created by root on 27/11/15.
 */
public class SocietyList {

    private String title, thumbnailUrl, desc;
    private int id;

    public SocietyList(){
    }

    public SocietyList(String title, String thumbnailUrl,int id, String desc){
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.id = id;
        this.desc = desc;
    }

    public String getSocietyTitle() {
        return title;
    }

    public void setSocietyTitle(String name) {
        this.title = name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
