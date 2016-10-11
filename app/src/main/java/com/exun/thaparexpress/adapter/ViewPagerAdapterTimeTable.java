package com.exun.thaparexpress.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.exun.thaparexpress.activity.fragments.TimeTableTabs.Friday;
import com.exun.thaparexpress.activity.fragments.TimeTableTabs.Monday;
import com.exun.thaparexpress.activity.fragments.TimeTableTabs.Thursday;
import com.exun.thaparexpress.activity.fragments.TimeTableTabs.Tuesday;
import com.exun.thaparexpress.activity.fragments.TimeTableTabs.Wednesday;

/**
 * Created by root on 8/15/16.
 */
public class ViewPagerAdapterTimeTable extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapterSocieties is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapterSocieties is created
    String branch;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapterTimeTable(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb,String branch) {
        super(fm);
        this.branch=branch;
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {


        Bundle bundle = new Bundle();
        bundle.putString("branch", branch);

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            Monday monday = new Monday();
            //monday.setArguments(bundle);
            return monday;
        }
        else if (position == 1)            // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            Tuesday tuesday = new Tuesday();
            return tuesday;
        }
        else if(position == 2)
        {
            Wednesday wednesday = new Wednesday();
            return wednesday;
        }

        else if(position == 3)
        {
            Thursday thursday = new Thursday();
            return thursday;
        }
        else
        {
             Friday friday = new Friday();
            return friday;
        }



    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}
