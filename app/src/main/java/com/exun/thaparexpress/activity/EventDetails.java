package com.exun.thaparexpress.activity;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.exun.thaparexpress.R;
import com.exun.thaparexpress.adapter.AppController;
import com.exun.thaparexpress.adapter.CustomEventDetailsAdapter;
import com.exun.thaparexpress.model.EventsDetailsList;

import java.util.ArrayList;
import java.util.List;

public class EventDetails extends AppCompatActivity {

    // Log tag
    private static final String TAG ="EventsDetail";

    /*// events json url
    private static final String url = AppConfig.URL_EVENT_DEIAILS;*/
    private ProgressDialog pDialog;
    TextView title,date,desc;
    NetworkImageView image;
    private List<EventsDetailsList> eventList = new ArrayList<EventsDetailsList>();
    private ListView listView;
    private CustomEventDetailsAdapter adapter;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bun = getIntent().getExtras();
        String sTitle="Event",sDate="Date", sVenue="Cos",sCost="Free", sDesc= " ";
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, r.getDisplayMetrics());
        String imageURL = "https://placeholdit.imgix.net/~text?txtsize=66&txt=" + width + "%C3%97"+px+"&w=" + width + "&h="+px;
        String sTime="Abhi",sHead[] = {"Venue" , "Cost", "Date", "Time", "Description"};

        if (bun != null){
            sTitle = bun.getString("title");
            sDate = bun.getString("date");
            sVenue = bun.getString("venue");
            sCost = bun.getString("cost");
            sTime = bun.getString("time");
            sDesc = bun.getString("desc");
            imageURL = bun.getString("image");
        }

        TextView venue,cost, time, date, desc;
        venue = (TextView) findViewById(R.id.venue);
        cost = (TextView) findViewById(R.id.cost);
        time = (TextView) findViewById(R.id.time);
        desc = (TextView) findViewById(R.id.desc);
        date = (TextView) findViewById(R.id.date);


        getSupportActionBar().setTitle(sTitle);

        venue.setText(sVenue);
        cost.setText(sCost);
        date.setText(sDate);
        desc.setText(sDesc);
        time.setText(sTime);

        image = (NetworkImageView) findViewById(R.id.image);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        image.setImageUrl(imageURL,imageLoader);


        /*pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        // Creating volley request obj
        JsonArrayRequest eventReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();

                        // Parsing json
                        try {

                            JSONObject obj = response.getJSONObject(0);

                            desc.setText(obj.getString("title"));
                            if (imageLoader == null)
                                imageLoader = AppController.getInstance().getImageLoader();
                            image.setImageUrl(obj.getString("image"), imageLoader);

                            JSONArray genreArry = obj.getJSONArray("genre");
                            for (int j = 0; j < genreArry.length(); j++) {
                                EventsDetailsList event = new EventsDetailsList();
                                event.setDetailHead(obj.getString("title"));
                                event.setDetailText(obj.getString("title"));

                                // adding event to events array
                                eventList.add(event);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(eventReq);*/
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

    /**** Method for Setting the Height of the ListView dynamically.
     **** Hack to fix the issue of not showing all the items of the ListView
     **** when placed inside a ScrollView  ****/
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
