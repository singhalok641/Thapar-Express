package com.exun.thaparexpress.model;

/**
 * Created by root on 29/11/15.
 */
public class StoreList {

    private String name, price, cat,condition,timer,sName,sPhone,sEmail,sRoll;
    private int id,userid;

    public StoreList(){
    }

    public StoreList(String name, String price, int id,int userid, String cat,String condition,
                     String timer,String sName ,String sEmail ,String sRoll ,String sPhone){
        this.name = name;
        this.price = price;
        this.id = id;
        this.userid = userid;
        this.cat = cat;
        this.condition = condition;
        this.timer = timer;
        this.sName = sName;
        this.sEmail = sEmail;
        this.sRoll = sRoll;
        this.sPhone = sPhone;
    }

    public int getId() {
        return id;
    }

    public int getUserid() {
        return userid;
    }

    public String getCat() {
        return cat;
    }

    public String getCondition() {
        return condition;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getTimer() {
        return timer;
    }

    public String getsEmail() {
        return sEmail;
    }

    public String getsName() {
        return sName;
    }

    public String getsPhone() {
        return sPhone;
    }

    public String getsRoll() {
        return sRoll;
    }

    public void setsEmail(String sEmail) {
        this.sEmail = sEmail;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public void setsPhone(String sPhone) {
        this.sPhone = sPhone;
    }

    public void setsRoll(String sRoll) {
        this.sRoll = sRoll;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
