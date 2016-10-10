package com.exun.thaparexpress.activity.fragments.TimeTableTabs;

import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exun.thaparexpress.Helper.SQLiteHandler;
import com.exun.thaparexpress.Helper.SQlite;
import com.exun.thaparexpress.R;
import com.exun.thaparexpress.adapter.TimeTableAdapter;

/**
 * Created by root on 8/14/16.
 */
public class Thursday  extends Fragment {

    // Log tag
    private static final String TAG = "Thursday";
    private Cursor thursdayList;

    private RecyclerView recyclerView;
    private SQlite db;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    private ProgressDialog pDialog;

    private View rootView;

    //String to store batch
    public String batch_code;
    public String year;


    private SQLiteHandler database;

    public Thursday() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_timetable, container, false);

        //SQLiteHandler
        database = new SQLiteHandler(getActivity().getApplicationContext());
        batch_code=database.getUserDetails().get("batch_code");
        year=database.getUserDetails().get("year");

        //Sqlite
        Log.e("database initialised", TAG);
        db = SQlite.getInstance(getActivity().getApplicationContext());

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv);
        populateLecturesList();

        return rootView;


    }

    private void populateLecturesList() {

        String table="";
        if(year.equals("2020")) {
            table = "firstyear";

        }
        else if(year.equals("2019"))
            table="timetable";

        Log.e(TAG,batch_code);

        String columns = batch_code+"_lecture AS lecture, "+batch_code+"_teacher AS teacher, "+batch_code+"_venue AS venue, "+"start_time, "+"end_time";

        String where = "id = '4'";
        thursdayList = db.fetchColumnsByCondition(table, columns, where);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new TimeTableAdapter(getActivity(), thursdayList);
        recyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onResume() {
        super.onResume();

        ((TimeTableAdapter) mAdapter).setOnItemClickListener(new TimeTableAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                //Intent i =new Intent(getActivity(), EventDetails.class);
                //i.putExtra("event_id",position);
                //getActivity().startActivity(i);
            }
        });


    }
}
