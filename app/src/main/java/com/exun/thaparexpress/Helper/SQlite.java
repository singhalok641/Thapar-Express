package com.exun.thaparexpress.Helper;

/**
 * Created by root on 8/13/16.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class SQlite extends SQLiteAssetHelper {

    private static final String TAG =SQlite.class.getSimpleName();

    private static SQlite mInstance = null;

    //Constants
    private static final String DATABASE_NAME = "ug2.db";
    private static final int DATABASE_VERSION = 1;

    public static SQlite getInstance(Context context){

        if (mInstance== null){
            mInstance = new SQlite(context);
        }

        return mInstance;
    }

    private SQlite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public Cursor fetchAll(String TABLE) {
        String query = "SELECT * FROM " + TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();

        return cursor;
    }

    public Cursor fetchByCondition(String TABLE,String where) {
        String query = "SELECT * FROM " + TABLE + " WHERE " + where;
        Log.d(TAG,query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();

        return cursor;
    }

    public Cursor fetchColumnsByCondition(String TABLE,String columns,String where) {
        String query = "SELECT "+ columns +" FROM " + TABLE + " WHERE " + where;
        Log.d(TAG,query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();

        return cursor;
    }



    public Cursor fetchByQuery(String TABLE, String queryText) {
        String query = "SELECT * FROM " + TABLE + queryText;
        Log.d(TAG,query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();

        return cursor;
    }

    /**
     * Getting row count of given table
     * */
    public int getRowCount(String TABLE) {
        String countQuery = "SELECT  * FROM " + TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        // return row count
        return cursor.getCount();
    }

    /**
     * Re create database Delete all tables and create them again
     * */
    public void delete(String Table) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(Table, null, null);
        db.close();

        Log.d(TAG, "Deleted all "+ Table + " info from sqlite");
    }
}


