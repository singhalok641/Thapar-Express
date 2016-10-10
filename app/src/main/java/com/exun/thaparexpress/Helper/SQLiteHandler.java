package com.exun.thaparexpress.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 6;

    // Database Name
    private static final String DATABASE_NAME = "ThaparExpress";

    // Login table name
    private static final String TABLE_USER = "users";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ROLL = "roll";
    private static final String KEY_HOSTEL = "hostel";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_BRANCH = "branch";
    private static final String KEY_YEAR = "year";
    private static final String KEY_url = "url";
    private static final String KEY_BATCH_CODE = "batch_code";


    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
            + KEY_EMAIL + " TEXT UNIQUE," + KEY_ROLL + " TEXT,"
            + KEY_HOSTEL + " TEXT," + KEY_GENDER + " TEXT," + KEY_PHONE + " TEXT," +
            KEY_BRANCH + " TEXT," + KEY_YEAR + " TEXT," + KEY_BATCH_CODE+ "TEXT," + KEY_url+"TEXT"+");";



    private static final String DATABASE_ALTER_TEAM_2 = "ALTER TABLE "
            + TABLE_USER + " ADD COLUMN " + KEY_BATCH_CODE + " TEXT;";
    private static final String DATABASE_ALTER_TEAM_1 = "ALTER TABLE "
            + TABLE_USER + " ADD COLUMN " + KEY_url + " TEXT;";

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_LOGIN_TABLE);
        db.execSQL(DATABASE_ALTER_TEAM_1);
        db.execSQL(DATABASE_ALTER_TEAM_2);
        Log.e(TAG,"altered");

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        //Log.e(TAG,"OnUpgrade");
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(int id,String name, String email, String roll
            , String hostel,String gender, String phone,  String branch, String year,String batch_code,String url) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID,id); //Id
        values.put(KEY_NAME, name); // Name
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_ROLL, roll); // ROll
        values.put(KEY_HOSTEL, hostel); // Hostel
        values.put(KEY_GENDER, gender); // Gender
        values.put(KEY_PHONE, phone); // Phone
        values.put(KEY_BRANCH, branch); // Branch
        values.put(KEY_YEAR, year); // Year
        values.put(KEY_BATCH_CODE,batch_code); //batch
        values.put(KEY_url,url);//photo_url

        // Inserting Row
        long Sid = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + Sid);

    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
//        Log.e(TAG,cursor.getString(10)+" " +cursor.getString(9)+" "+cursor.getString(1));
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("id",cursor.getString(0));
            user.put("name", cursor.getString(1));
            user.put("email", cursor.getString(2));
            user.put("roll", cursor.getString(3));
            user.put("hostel", cursor.getString(4));
            user.put("gender", cursor.getString(5));
            user.put("phone", cursor.getString(6));
            user.put("branch", cursor.getString(7));
            user.put("year", cursor.getString(8));
            user.put("batch_code",cursor.getString(cursor.getColumnIndex(KEY_BATCH_CODE)));
            user.put("url",cursor.getString(cursor.getColumnIndex(KEY_url)));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

}
