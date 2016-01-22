package com.exun.thaparexpress.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.exun.thaparexpress.activity.fragments.SocietiesTabs.Cultural;
import com.exun.thaparexpress.activity.fragments.SocietiesTabs.StuChaps;
import com.exun.thaparexpress.activity.fragments.SocietiesTabs.Technical;

/**
 * Created by n00b on 11/26/2015.
 */
public class ViewPagerAdapterSocieties extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapterSocieties is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapterSocieties is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapterSocieties(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            Cultural cultural = new Cultural();
            return cultural;
        }
        else if (position == 1)            // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            StuChaps sc = new StuChaps();
            return sc;
        }
        else
        {
            Technical tech = new Technical();
            return tech;
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
