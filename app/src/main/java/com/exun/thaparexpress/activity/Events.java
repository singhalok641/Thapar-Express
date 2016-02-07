package com.exun.thaparexpress.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.exun.thaparexpress.R;
import com.exun.thaparexpress.adapter.SlidingTabLayout;
import com.exun.thaparexpress.adapter.ViewPagerAdapterEvents;

/**
 * Created by n00b on 11/25/2015.
 */
public class Events extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener{

    Toolbar toolbar;
    private FragmentDrawer drawerFragment;
    ViewPager pager;
    ViewPagerAdapterEvents adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Cultural","Technical", "Others"};
    int Numboftabs =3;
    private boolean doubleBackToExitPressedOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        // Creating The Toolbar and setting it as the Toolbar for the activity

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        drawerFragment.setDrawerListener(this);


        // Creating The ViewPagerAdapterSocieties and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapterEvents(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pagerEvents);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(2);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabsEvents);
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
        Intent i = new Intent(Events.this,MainActivity.class);
        switch (position) {
            case 0:
                i.putExtra("selectionId",0);
                startActivity(i);
                finish();
                break;
            case 1:
                i = new Intent(Events.this,Societies.class);
                startActivity(i);
                finish();
                break;
            case 2:

                break;
            case 3:
                i.putExtra("selectionId",3);
                startActivity(i);
                finish();
                break;
            case 4:
                i.putExtra("selectionId", 4);
                startActivity(i);
                finish();
                break;
            case 5:
                Toast.makeText(getApplicationContext(), "Coming soon :D", Toast.LENGTH_SHORT).show();
                break;
            case 6:
                i.putExtra("selectionId",6);
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

        Intent i = new Intent(Events.this,MainActivity.class);
        i.putExtra("selectionId",0);
        startActivity(i);
        finish();
    }
}
