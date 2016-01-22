package com.exun.thaparexpress.model;

/**
 * Created by root on 29/11/15.
 */
public class EventsList {

    private String eventName, date, eventDesc, eventShortDesc, eventVenue, eventTime, eventCost, eventImage;
    private int eventId, socID;

    public EventsList(){
    }

    public EventsList(String eventName, String date, String eventDesc, String eventShortDesc, String eventVenue, String eventTime,
                       String eventImage, String eventCost, int eventId, int socID){
        this.eventName = eventName;
        this.date = date;
        this.eventDesc = eventDesc;
        this.eventShortDesc = eventShortDesc;
        this.eventVenue = eventVenue;
        this.eventTime = eventTime;
        this.eventCost = eventCost;
        this.eventImage = eventImage;
        this.eventId = eventId;
        this.socID = socID;
    }

    public String getDate() {
        return date;
    }

    public String getEventName() {
        return eventName;
    }

    public int getEventId() {
        return eventId;
    }

    public int getSocID() {
        return socID;
    }

    public String getEventCost() {
        return eventCost;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public String getEventShortDesc() {
        return eventShortDesc;
    }

    public String getEventTime() {
        return eventTime;
    }

    public String getEventVenue() {
        return eventVenue;
    }

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEventCost(String eventCost) {
        this.eventCost = eventCost;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public void setEventShortDesc(String eventShortDesc) {
        this.eventShortDesc = eventShortDesc;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public void setEventVenue(String eventVenue) {
        this.eventVenue = eventVenue;
    }

    public void setSocID(int socID) {
        this.socID = socID;
    }
}
