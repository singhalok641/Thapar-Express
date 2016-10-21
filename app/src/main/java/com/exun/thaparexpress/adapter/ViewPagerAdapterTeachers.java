package com.exun.thaparexpress.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.exun.thaparexpress.activity.fragments.TeachersDepartmentTabs.BiotechDepartment;
import com.exun.thaparexpress.activity.fragments.TeachersDepartmentTabs.ChemicalDepartment;
import com.exun.thaparexpress.activity.fragments.TeachersDepartmentTabs.CivilDepartment;
import com.exun.thaparexpress.activity.fragments.TeachersDepartmentTabs.ComputerScienceDepartment;
import com.exun.thaparexpress.activity.fragments.TeachersDepartmentTabs.ElectricalInstrumentationDepartment;
import com.exun.thaparexpress.activity.fragments.TeachersDepartmentTabs.ElectronicsCommunicationDepartment;
import com.exun.thaparexpress.activity.fragments.TeachersDepartmentTabs.MechanicalDepartment;

/**
 * Created by root on 10/17/16.
 */
public class ViewPagerAdapterTeachers extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapterSocieties is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapterSocieties is created
    String branch;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapterTeachers(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
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
            ComputerScienceDepartment computer= new ComputerScienceDepartment();
            //monday.setArguments(bundle);
            return computer;
        }
        else if (position == 1)            // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            ElectronicsCommunicationDepartment electronics = new ElectronicsCommunicationDepartment();
            return electronics;
        }
        else if(position == 2)
        {
            MechanicalDepartment mech = new MechanicalDepartment();
            return mech;
        }

        else if(position == 3)
        {
            ElectricalInstrumentationDepartment electrical = new ElectricalInstrumentationDepartment();
            return electrical;
        }
        else if(position==4)
        {
            CivilDepartment civil = new CivilDepartment();
            return civil;
        }

        else if(position==5)
        {
            ChemicalDepartment chemical = new ChemicalDepartment();
            return chemical;
        }
        else
        {
            BiotechDepartment bio = new BiotechDepartment();
            return bio;
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
