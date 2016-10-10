package com.exun.thaparexpress.activity.fragments.SocietiesTabs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.exun.thaparexpress.activity.SocDetails;
import com.exun.thaparexpress.adapter.AppConfig;
import com.exun.thaparexpress.R;
import com.exun.thaparexpress.adapter.AppController;
import com.exun.thaparexpress.adapter.CustomSocietyListAdapter;
import com.exun.thaparexpress.model.SocietyList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by n00b on 11/25/2015.
 */
public class Technical extends Fragment{

    // Log tag
    private static final String TAG = "Technical Tab";

    // Society- Technical json url
    private static final String url = AppConfig.URL_SOCIETY_LIST + "/Technical";
    private ProgressDialog pDialog;
    private List<SocietyList> societyList = new ArrayList<SocietyList>();
    private ListView listView;
    private CustomSocietyListAdapter adapter;

    public Technical() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_societies_technical, container, false);

        // use rootView.findViewById for fragments
        listView = (ListView) rootView.findViewById(R.id.listTech);
        adapter = new CustomSocietyListAdapter(getActivity(), societyList);
        listView.setAdapter(adapter);

        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        // Creating volley request obj
        StringRequest societyReq = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Society: " + response);
                        hidePDialog();

                        try {
                            JSONObject jObj = new JSONObject(response);
                            String status = jObj.getString("status");

                            // Check for error node in json
                            if (status.equals("success")) {

                                // Now store the user in SQLite
                                String message = jObj.getString("message");

                                Log.d(TAG, "Society " + message);

                                JSONArray data = jObj.getJSONArray("data");

                                if (data.length() > 0) {

                                    for (int k = 0; k < data.length(); k++) {

                                        JSONObject obj = data.getJSONObject(k);
                                        SocietyList society = new SocietyList();

                                        society.setSocietyTitle(obj.getString("name"));
                                        society.setId(obj.getInt("id"));
                                        society.setDesc(obj.getString("short_description"));

                                        String image = obj.getString("image");
                                        if (image.equals("null")){
                                            if (obj.getInt("id") == 44){
                                                Resources r = getResources();
                                                float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, r.getDisplayMetrics());
                                                float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, r.getDisplayMetrics());
                                                image = "https://placeholdit.imgix.net/~text?txtsize=66&txt=" + width + "%C3%97"+px+"&w=" + width + "&h="+px;
                                            }
                                            else {
                                                image = "http://thapar.brinjal.in/images/soc/thumbnails/tn_" + obj.getInt("id") + ".png";
                                            }
                                        }

                                        society.setThumbnailUrl(image);

                                        // adding society to society array
                                        societyList.add(society);
                                    }
                                }

                            } else {
                                // Error in login. Get the error message
                                String errorMsg = jObj.getString("message");
                                Log.d(TAG,errorMsg);
                            }
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                            Log.d("Society", "Json error: " + e.getMessage());
                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                hidePDialog();
            }

        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(societyReq);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                SocietyList s = societyList.get(i);
                String desc = s.getDesc();
                String title = s.getSocietyTitle();
                int id = s.getId();

                Intent intent = new Intent(getActivity(), SocDetails.class);
                intent.putExtra("title", title);
                intent.putExtra("id", id);
                intent.putExtra("desc",desc);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return rootView;
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
}
