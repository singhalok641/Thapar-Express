package com.exun.thaparexpress.activity.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.exun.thaparexpress.activity.FullBlog;
import com.exun.thaparexpress.adapter.AppConfig;
import com.exun.thaparexpress.R;
import com.exun.thaparexpress.adapter.AppController;
import com.exun.thaparexpress.adapter.RVBlogAdapter;
import com.exun.thaparexpress.model.BlogsList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by n00b on 11/25/2015.
 */
public class ThaparLogs extends Fragment{

    // Log tag
    private static final String TAG ="Blogs";

    // blogs json url
    private static final String url = AppConfig.URL_BLOGS_LIST;
    private ProgressDialog pDialog;
    private List<BlogsList> blogList = new ArrayList<BlogsList>();

    //New
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tlogs, container, false);

        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.blog_rv);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RVBlogAdapter(blogList);
        mRecyclerView.setAdapter(mAdapter);

        // Creating volley request obj
        StringRequest blogReq = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Blogs: " + response);
                        hidePDialog();

                        try {
                            JSONObject jObj = new JSONObject(response);
                            String status = jObj.getString("status");

                            // Check for error node in json
                            if (status.equals("success")) {

                                // Now store the user in SQLite
                                String message = jObj.getString("message");

                                Log.d(TAG, "Blogs " + message);

                                JSONArray data = jObj.getJSONArray("data");

                                if (data.length() > 0){

                                    for (int k = 0; k< data.length();k++){

                                        JSONObject obj = data.getJSONObject(k);
                                        BlogsList blog = new BlogsList();
                                        blog.setBlogName(obj.getString("name"));
                                        blog.setBlogId(obj.getInt("id"));
                                        blog.setBlogDate(obj.getString("timer"));
                                        blog.setBlogText(obj.getString("content"));

                                        // adding blog to blogs array
                                        blogList.add(blog);
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
                            Toast.makeText(getActivity(),
                                    "Connection failed! :(", Toast.LENGTH_LONG).show();
                            Log.d("Events","Json error: " + e.getMessage());
                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        mAdapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        "Connection failed! :(", Toast.LENGTH_LONG).show();
                hidePDialog();
            }

        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(blogReq);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((RVBlogAdapter) mAdapter).setOnItemClickListener(new RVBlogAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                Intent i = new Intent(getActivity(), FullBlog.class);
                BlogsList m = blogList.get(position);

                String blogName = "a",blogDate= "a",blogText= "a";
                int blogId = 1;

                 blogName = m.getBlogName();
                 blogDate = m.getBlogDate();
                 blogId = m.getBlogId();
                 blogText = m.getBlogText();

                i.putExtra("name", blogName);
                i.putExtra("text", blogText);
                i.putExtra("date", blogDate);
                i.putExtra("id", blogId);

                startActivity(i);
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
}
