package com.exun.thaparexpress.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.github.ybq.android.spinkit.style.WanderingCubes;

/**
 * Created by n00b on 1/17/2016.
 */
public class AppConfig {

    // Loading Constants
    public WanderingCubes wanderingCubes = new WanderingCubes();
    public ThreeBounce threeBounce = new ThreeBounce();


    // Home fragment updates url
    public static String URL_UPDATES = "http://thapar.brinjal.in/api/v1/event_updates";

    // Home fragment updates url
    public static String URL_SOCIETY_LIST = "http://thapar.brinjal.in/api/v1/societies";

    // Event details url
    public static String URL_EVENT_DEIAILS = "http://api.androidhive.info/json/movies.json";

    // Event details url
    public static String URL_SOCIETY_MEMB = "http://thapar.brinjal.in/api/v1/societies_members/";

    // Selected society's details url
    public static String URL_SOCIETY_DETAILS = "http://thapar.brinjal.in/api/v1/society_details/";

    // Blogs list url
    public static String URL_BLOGS_LIST = "http://thapar.brinjal.in/api/v1/blog_posts";

    // Events list url
    public static String URL_EVENTS_LIST = "http://thapar.brinjal.in/api/v1/events/";

    // Events list url
    public static String URL_LOGIN = "http://thapar.brinjal.in/api/v2/login";

    // Events list url
    public static String URL_REGISTER = "http://thapar.brinjal.in/api/v2/register";

    // Events list url
    public static String URL_STORE_LIST = "http://thapar.brinjal.in/api/v1/store_items";

    // Events list url
    public static String URL_ADD_STORE_ITEM = "http://thapar.brinjal.in/api/v2/add_store_item";

    //SpinKit View
    public static Sprite getLoadingDrawable(Context context, int selection, int color){

        Sprite drawable = new WanderingCubes();

        switch (selection){
            case 0:
                drawable = new ThreeBounce();
                break;
            case 1:
                drawable = new WanderingCubes();
                break;
        }

        if (color != -1)
            drawable.setColor(ContextCompat.getColor(context.getApplicationContext(), color));

        return drawable;
    }





}
