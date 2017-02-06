package com.exun.thaparexpress.activity.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.exun.thaparexpress.activity.MainActivity;
import com.exun.thaparexpress.activity.ProfileActivity;
import com.exun.thaparexpress.adapter.AppConfig;
import com.exun.thaparexpress.R;
import com.exun.thaparexpress.adapter.AppController;
import com.exun.thaparexpress.adapter.CustomUpdateListAdapter;
import com.exun.thaparexpress.model.BlogsList;
import com.exun.thaparexpress.model.UpdatesList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by n00b on 11/25/2015.
 */
public class Home extends Fragment{

    // Log tag
    private static final String TAG = "Home";

    // Updates json url
    private static final String url = AppConfig.URL_UPDATES;
    private ProgressDialog pDialog;
    private List<UpdatesList> updatesLists = new ArrayList<UpdatesList>();
    private ListView listView;
    Random random = new Random();
    private CustomUpdateListAdapter adapter;
    Resources res;
    int[] colors;

    //BottomBar
    private RelativeLayout btnHome, btnProfile, btnEvents, btnBlogs,btnNotifications;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        btnHome = (RelativeLayout) rootView.findViewById(R.id.button_home);
        btnProfile = (RelativeLayout) rootView.findViewById(R.id.button_profile);
        btnEvents = (RelativeLayout) rootView.findViewById(R.id.button_events);
        btnBlogs = (RelativeLayout) rootView.findViewById(R.id.button_blogs);
        btnNotifications = (RelativeLayout) rootView.findViewById(R.id.button_notifications);

        listView = (ListView) rootView.findViewById(R.id.listUpdates);
        adapter = new CustomUpdateListAdapter(getActivity(), updatesLists);
        listView.setAdapter(adapter);

        res = getResources();
        colors = res.getIntArray(R.array.colors);

        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        // Creating volley request obj
        StringRequest updatesReq = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Updates: " + response);
                        hidePDialog();

                        try {
                            JSONObject jObj = new JSONObject(response);
                            String status = jObj.getString("status");

                            // Check for error node in json
                            if (status.equals("success")) {

                                // Now store the user in SQLite
                                String message = jObj.getString("message");

                                Log.d(TAG, "Updates " + message);

                                JSONArray data = jObj.getJSONArray("data");

                                if (data.length() > 0){

                                    for (int k = 0; k< data.length();k++){

                                        JSONObject obj = data.getJSONObject(k);
                                        UpdatesList updates = new UpdatesList();
                                        updates.setHead(obj.getString("title"));
                                        updates.setUpdate(obj.getString("message"));

                                        int color = getColor(obj.getString("color"));
                                        updates.setRandomColor(color);

                                        // adding updates to updates array
                                        updatesLists.add(updates);
                                    }
                                }

                            } else {
                                // Error in login. Get the error message
                                String errorMsg = jObj.getString("message");
                                Toast.makeText(getActivity(),
                                        "Connection failed! :(", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                            Log.d(TAG,"Json error: " + e.getMessage());
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "Connection lost! :(", Toast.LENGTH_LONG).show();
                        }

                        if (updatesLists == null){
                            UpdatesList updates = new UpdatesList();
                            updates.setHead("Nope");
                            updates.setUpdate("Nothing new right now!");

                            int color = colors[random.nextInt(4)];
                            updates.setRandomColor(color);

                            // adding updates to updates array
                            updatesLists.add(updates);
                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                UpdatesList updates = new UpdatesList();
                updates.setHead("Nope");
                updates.setUpdate("Nothing new right now!");

                int color = colors[random.nextInt(4)];
                updates.setRandomColor(color);

                // adding updates to updates array
                updatesLists.add(updates);
                adapter.notifyDataSetChanged();
                Toast.makeText(getActivity(),
                        "Connection failed! :(", Toast.LENGTH_LONG).show();
                hidePDialog();
            }

        });

        setClickListeners();

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(updatesReq);

        // Inflate the layout for this fragment
        return rootView;
    }

    private void setClickListeners() {
        btnHome.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                ((MainActivity) getActivity()).displayView(0);
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Intent i = new Intent(getActivity(), ProfileActivity.class);
                startActivity(i);
            }
        });

        btnEvents.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                ((MainActivity) getActivity()).displayView(2);
            }
        });

        btnBlogs.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                ((MainActivity) getActivity()).displayView(5);
            }
        });

        btnNotifications.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                ((MainActivity) getActivity()).displayView(0);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    private int getColor(String name){

        int color = 878787;

        switch (name){
            case "alert-danger":
                color = res.getColor(R.color.danger);
                break;
            case "alert-info":
                color = res.getColor(R.color.info);
                break;
            case "alert-success":
                color = res.getColor(R.color.success);
                break;
            case "alert-warning":
                color = res.getColor(R.color.warning);
                break;
        }
        return color;
    }
}
