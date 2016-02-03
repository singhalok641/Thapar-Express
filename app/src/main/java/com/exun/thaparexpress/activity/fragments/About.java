package com.exun.thaparexpress.activity.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exun.thaparexpress.R;

/**
 * Created by n00b on 11/25/2015.
 */
public class About extends Fragment{

    // Log tag
    private static final String TAG ="About";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);


        // Inflate the layout for this fragment
        return rootView;
    }
}
