package com.exun.thaparexpress.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.exun.thaparexpress.Helper.SQLiteHandler;
import com.exun.thaparexpress.R;
import com.exun.thaparexpress.adapter.SlidingTabLayout;
import com.exun.thaparexpress.adapter.ViewPagerAdapterMess;

import java.util.Calendar;

/**
 * Created by root on 10/11/16.
 */
public class MessMenu extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener
{

    Toolbar toolbar;
    private FragmentDrawer drawerFragment;
    ViewPager pager;
    ViewPagerAdapterMess adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"Monday", "Tuesday", "Wednesday","Thursday","Friday","Saturday","Sunday"};
    int Numboftabs = 7;

    int pos;
    String branch;
    private SQLiteHandler database;
    public String hostel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess);

        //SQLiteHandler
        database = new SQLiteHandler(this.getApplicationContext());
        hostel=database.getUserDetails().get("hostel");

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                pos=6;
                break;
            // Current day is Sunday

            case Calendar.MONDAY:
                // Current day is Monday
                pos=0;
                break;

            case Calendar.TUESDAY:
                // etc.
                pos=1;
                break;


            case Calendar.WEDNESDAY:
                // etc.
                pos=2;
                break;

            case Calendar.THURSDAY:
                // etc.
                pos=3;
                break;

            case Calendar.FRIDAY:
                // etc.
                pos=4;
                break;

            case Calendar.SATURDAY:
                // etc.
                pos=5;
                break;
        }



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        drawerFragment.setDrawerListener(this);

// Creating The ViewPagerAdapterTimetable and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter = new ViewPagerAdapterMess(getSupportFragmentManager(), Titles, Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager_mess);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(2);

        //function to set the current day timetable tab
        pager.setCurrentItem(pos);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs_mess);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);



    }


    @Override
    public void onDrawerItemSelected(View view, int position) {

        displayView(position);

        
    }

    private void displayView(int position) {
        Intent i = new Intent(MessMenu.this,MainActivity.class);
        switch (position) {
            case 0:
                i.putExtra("selectionId",0);
                startActivity(i);
                finish();
                break;
            case 1:
                i = new Intent(MessMenu.this,Societies.class);
                startActivity(i);
                finish();
                break;
            case 3:
                i.putExtra("selectionId", 3);
                startActivity(i);
                finish();
                break;
            case 4:

                break;
            case 5:
                i.putExtra("selectionId",5);
                startActivity(i);
                finish();
                break;
            case 6:
                i.putExtra("selectionId",6);
                startActivity(i);
                finish();
                break;
            case 7:
                Toast.makeText(getApplicationContext(), "Coming soon :D", Toast.LENGTH_SHORT).show();
                break;
            case 8:
                i.putExtra("selectionId",8);
                startActivity(i);
                finish();
                break;
            case 9:
                i.putExtra("selectionId",9);
                startActivity(i);
                finish();
                break;
            case 10:
                i.putExtra("selectionId",10);
                startActivity(i);
                finish();
                break;
            default:
                i.putExtra("selectionId",0);
                startActivity(i);
                finish();
                break;
        }

    }
    @Override
    public void onBackPressed() {

        Intent i = new Intent(MessMenu.this,MainActivity.class);
        i.putExtra("selectionId",0);
        startActivity(i);
        finish();
    }
}
