package com.exun.thaparexpress.model;

import android.database.Cursor;

/**
 * Created by root on 8/13/16.
 */
public class TimeTableModel {

    String lecture,teacher,venue,start_time,end_time;

    String id;

    public TimeTableModel(Cursor cursor)
    {

        lecture= cursor.getString(cursor.getColumnIndex("lecture"));
        teacher= cursor.getString(cursor.getColumnIndex("teacher"));
        venue= cursor.getString(cursor.getColumnIndex("venue"));
        start_time=cursor.getString(cursor.getColumnIndex("start_time"));
        end_time=cursor.getString(cursor.getColumnIndex("end_time"));

    }

    public void setTeacher(String teacher) {
        this.teacher =teacher;
    }
    public String getTeacher() {return teacher;}


    public void setLecture(String lecture) {
        this.lecture =lecture;
    }
    public String getLecture() {
        return lecture;
    }


    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }


    public void setVenue(String venue) {
        this.venue =venue;
    }
    public String getVenue() {
        return venue;
    }

    public void setStart_time(String start_time) {this.start_time =start_time;}
    public String getStart_time() {
        return start_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time =end_time;
    }
    public String getEnd_time() {
        return end_time;
    }


}
