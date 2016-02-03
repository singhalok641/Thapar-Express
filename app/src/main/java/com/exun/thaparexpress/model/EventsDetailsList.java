package com.exun.thaparexpress.model;

/**
 * Created by root on 29/11/15.
 */
public class EventsDetailsList {

    private String detailText, detailHead;

    public EventsDetailsList(){
    }

    public EventsDetailsList(String detailHead, String detailText){
        this.detailText = detailText;
        this.detailHead = detailHead;
    }

    public String getDetailHead() {
        return detailHead;
    }

    public String getDetailText() {
        return detailText;
    }

    public void setDetailHead(String detailHead) {
        this.detailHead = detailHead;
    }

    public void setDetailText(String detailText) {
        this.detailText = detailText;
    }
}
