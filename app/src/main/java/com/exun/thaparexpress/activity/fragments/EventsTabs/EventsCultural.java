package com.exun.thaparexpress.activity.fragments.EventsTabs;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
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
import com.android.volley.toolbox.StringRequest;
import com.exun.thaparexpress.R;
import com.exun.thaparexpress.activity.EventDetails;
import com.exun.thaparexpress.adapter.AppConfig;
import com.exun.thaparexpress.adapter.AppController;
import com.exun.thaparexpress.adapter.CustomEventListAdapter;
import com.exun.thaparexpress.model.EventsList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by n00b on 1/16/2016.
 */
public class EventsCultural extends Fragment {

    // Log tag
    private static final String TAG = "EventsCultural";

    // events json url
    private static final String url = AppConfig.URL_EVENTS_LIST + "Cultural";
    private ProgressDialog pDialog;
    private List<EventsList> eventList = new ArrayList<EventsList>();
    private ListView listView;
    private CustomEventListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_events_cultural, container, false);

        listView = (ListView) rootView.findViewById(R.id.listEvents);
        adapter = new CustomEventListAdapter(getActivity(), eventList);
        listView.setAdapter(adapter);

        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        // Creating volley request obj
        StringRequest eventReq = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Events: " + response);
                        hidePDialog();

                        try {
                            JSONObject jObj = new JSONObject(response);
                            String status = jObj.getString("status");

                            // Check for error node in json
                            if (status.equals("success")) {

                                // Now store the user in SQLite
                                String message = jObj.getString("message");

                                Log.d(TAG, "Events " + message);

                                JSONArray data = jObj.getJSONArray("data");

                                if (data.length() > 0) {

                                    for (int k = 0; k < data.length(); k++) {

                                        JSONObject obj = data.getJSONObject(k);
                                        EventsList event = new EventsList();
                                        event.setEventId(obj.getInt("id"));
                                        event.setSocID(obj.getInt("societyid"));
                                        event.setEventName(obj.getString("name"));
                                        event.setEventDesc(obj.getString("description"));

                                        String date = obj.getString("date");
                                        if (!date.equals("null")){
                                            event.setDate(date);
                                        }
                                        else {
                                            event.setDate("15-09-2015");
                                        }

                                        event.setEventCost(obj.getString("cost"));
                                        event.setEventVenue(obj.getString("venue"));
                                        event.setEventTime(obj.getString("time"));

                                        String shortDesc = obj.getString("short_description");
                                        if (shortDesc.equals("null")) {
                                            event.setEventShortDesc(" ");
                                        } else {
                                            event.setEventShortDesc(shortDesc);
                                        }

                                        String image = obj.getString("image");
                                        if (!image.equals("null")) {
                                            String url = ("http://thaparexpress.com/superadmin/eventimg/" + image);
                                            event.setEventImage(url);
                                        } else {
                                            DisplayMetrics metrics = new DisplayMetrics();
                                            getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
                                            int width = metrics.widthPixels;
                                            Resources r = getResources();
                                            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, r.getDisplayMetrics());
                                            String imageURL = "https://placeholdit.imgix.net/~text?txtsize=66&txt=" + width + "%C3%97"+px+"&w=" + width + "&h="+px;
                                            event.setEventImage(imageURL);
                                        }

                                        // adding event to events array
                                        eventList.add(event);
                                    }
                                }

                            } else {
                                // Error in login. Get the error message
                                String errorMsg = jObj.getString("message");
                                Toast.makeText(getActivity(),
                                        errorMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                            Log.d("Events", "Json error: " + e.getMessage());
                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hidePDialog();
            }

        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(eventReq);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                EventsList s = eventList.get(i);
                int socId = s.getSocID(), id = s.getEventId();
                String title = s.getEventName();
                String date = s.getDate();
                String desc = s.getEventDesc();
                String time = s.getEventTime();
                String cost = s.getEventCost();
                String short_desc = s.getEventShortDesc();
                String image = s.getEventImage();
                String venue = s.getEventVenue();

                Intent intent = new Intent(getActivity(), EventDetails.class);
                intent.putExtra("title", title);
                intent.putExtra("date", date);
                intent.putExtra("desc", desc);
                intent.putExtra("time", time);
                intent.putExtra("cost", cost);
                intent.putExtra("short_desc", short_desc);
                intent.putExtra("image", image);
                intent.putExtra("venue", venue);
                intent.putExtra("socId", socId);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
