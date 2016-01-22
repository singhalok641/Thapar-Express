package com.exun.thaparexpress.model;

/**
 * Created by root on 29/11/15.
 */
public class BlogsList {

    private String blogName, blogDate, blogText;
    private int blogId;

    public BlogsList(){
    }

    public BlogsList(String blogName, String blogDate, int blogId, String blogText){
        this.blogDate = blogDate;
        this.blogName = blogName;
        this.blogId = blogId;
        this.blogText = blogText;
    }

    public String getBlogDate() {
        return blogDate;
    }

    public int getBlogId() {
        return blogId;
    }

    public String getBlogText() {
        return blogText;
    }

    public String getBlogName() {
        return blogName;
    }

    public void setBlogDate(String blogDate) {
        this.blogDate = blogDate;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public void setBlogText(String blogText) {
        this.blogText = blogText;
    }

    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }
}
