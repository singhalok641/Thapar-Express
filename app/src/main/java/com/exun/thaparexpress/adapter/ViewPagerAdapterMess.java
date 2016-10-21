package com.exun.thaparexpress.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.exun.thaparexpress.activity.fragments.MessMenuTabs.Mess_Friday;
import com.exun.thaparexpress.activity.fragments.MessMenuTabs.Mess_Monday;
import com.exun.thaparexpress.activity.fragments.MessMenuTabs.Mess_Saturday;
import com.exun.thaparexpress.activity.fragments.MessMenuTabs.Mess_Sunday;
import com.exun.thaparexpress.activity.fragments.MessMenuTabs.Mess_Thursday;
import com.exun.thaparexpress.activity.fragments.MessMenuTabs.Mess_Tuesday;
import com.exun.thaparexpress.activity.fragments.MessMenuTabs.Mess_Wednesday;

/**
 * Created by root on 10/16/16.
 */
public class ViewPagerAdapterMess extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapterSocieties is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapterSocieties is created
    String branch;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapterMess(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);
        this.branch=branch;
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }


    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {


        if(position == 0) // if the position is 0 we are returning the First tab
        {
            Mess_Monday monday = new Mess_Monday();
            //monday.setArguments(bundle);
            return monday;
        }
        else if (position == 1)            // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            Mess_Tuesday tuesday = new Mess_Tuesday();
            return tuesday;
        }
        else if(position == 2)
        {
            Mess_Wednesday wednesday = new Mess_Wednesday();
            return wednesday;
        }

        else if(position == 3)
        {
            Mess_Thursday thursday = new Mess_Thursday();
            return thursday;
        }
        else if(position==4)
        {
            Mess_Friday friday = new Mess_Friday();
            return friday;
        }

        else if(position==5)
        {
            Mess_Saturday saturday = new Mess_Saturday();
            return saturday;
        }
        else
        {
            Mess_Sunday sunday = new Mess_Sunday();
            return sunday;
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

