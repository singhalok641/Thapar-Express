package com.exun.thaparexpress.activity.fragments;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exun.thaparexpress.R;

/**
 * Created by n00b on 11/25/2015.
 */
public class About extends Fragment{

    // Log tag
    private static final String TAG ="About";

    TextView versionCode;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);

        versionCode = (TextView) rootView.findViewById(R.id.textViewVersion);
        PackageInfo pInfo = null;
        try {
            pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = pInfo.versionName;

        versionCode.setText("Version " + version);

        // Inflate the layout for this fragment
        return rootView;
    }
}
