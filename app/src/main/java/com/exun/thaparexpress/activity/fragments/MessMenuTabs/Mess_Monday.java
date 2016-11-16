package com.exun.thaparexpress.activity.fragments.MessMenuTabs;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.exun.thaparexpress.R;
import com.exun.thaparexpress.adapter.CustomMessMenuListAdapter;
import com.exun.thaparexpress.model.MessMenuList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 10/16/16.
 */
public class Mess_Monday extends Fragment {

    private View rootView;
    private ProgressDialog pDialog;
    private List<MessMenuList> messMenuLists = new ArrayList<MessMenuList>();
    private ListView listView;
    private CustomMessMenuListAdapter adapter;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_mess, container, false);

        adapter = new CustomMessMenuListAdapter(getActivity(), messMenuLists);
        listView.setAdapter(adapter);

        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        


        return rootView;
    }

    }

