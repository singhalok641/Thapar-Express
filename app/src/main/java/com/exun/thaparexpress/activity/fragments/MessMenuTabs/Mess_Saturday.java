package com.exun.thaparexpress.activity.fragments.MessMenuTabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exun.thaparexpress.R;

/**
 * Created by root on 10/16/16.
 */
public class Mess_Saturday extends Fragment {
    private View rootView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_mess, container, false);

        return rootView;
    }
}
