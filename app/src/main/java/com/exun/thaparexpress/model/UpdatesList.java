package com.exun.thaparexpress.model;

/**
 * Created by root on 27/11/15.
 */
public class UpdatesList {

    private String update,head;
    private int randomColor;

    public UpdatesList(){
    }

    public UpdatesList(String update,int randomColor, String head){
        this.update = update;
        this.randomColor =randomColor;
        this.head = head;
    }

    public String getUpdate() {
        return update;
    }

    public int getRandomColor() {
        return randomColor;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public void setRandomColor(int randomColor) {
        this.randomColor = randomColor;
    }

    public void setUpdate(String update) {
        this.update = update;
    }
}
