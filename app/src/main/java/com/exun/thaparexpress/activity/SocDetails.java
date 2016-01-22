package com.exun.thaparexpress.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.exun.thaparexpress.adapter.AppConfig;
import com.exun.thaparexpress.R;
import com.exun.thaparexpress.adapter.AppController;
import com.exun.thaparexpress.adapter.CustomSocMembGridAdapter;
import com.exun.thaparexpress.model.BlogsList;
import com.exun.thaparexpress.model.ExpandableHeightGridView;
import com.exun.thaparexpress.model.SocMembList;
import com.exun.thaparexpress.model.SocietyList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by n00b on 1/13/2016.
 */
public class SocDetails extends AppCompatActivity{

    // Log tag
    private static final String TAG ="Society Details";
    private Toolbar mToolbar;

    // society details json url
    private static String url = AppConfig.URL_SOCIETY_MEMB;
    private ProgressDialog pDialog;
    private List<SocMembList> socMembListList = new ArrayList<SocMembList>();
    ExpandableHeightGridView gridView;
    TextView socAbout;
    NetworkImageView socImage;
    private CustomSocMembGridAdapter adapter;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_soc_details);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        final int id = bundle.getInt("id");
        String imageURL = "http://thaparexpress.com/images/soc/" + id + ".png";
        url = AppConfig.URL_SOCIETY_MEMB  + id;
        String about = bundle.getString("desc");

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        socAbout = (TextView) findViewById(R.id.socAbout);
        socAbout.setText(about);
        socImage = (NetworkImageView) findViewById(R.id.socImage);
        socImage.setImageUrl(imageURL, imageLoader);
        gridView = (ExpandableHeightGridView) findViewById(R.id.gridView1);
        adapter = new CustomSocMembGridAdapter(this, socMembListList);
        gridView.setAdapter(adapter);
        gridView.setExpanded(true);

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        // Creating volley request obj
        StringRequest membReq = new StringRequest(Request.Method.GET, url,
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

                                // Json message
                                String message = jObj.getString("message");

                                Log.d(TAG, "Society " + message);

                                JSONArray data = jObj.getJSONArray("data");

                                if (data.length() > 0) {

                                    for (int k = 0; k < data.length(); k++) {

                                        JSONObject obj = data.getJSONObject(k);
                                        SocMembList socMembList = new SocMembList();
                                        socMembList.setId(obj.getInt("id"));
                                        socMembList.setPhone(obj.getInt("phone"));
                                        socMembList.setName(obj.getString("name"));
                                        socMembList.setDesi(obj.getString("position"));
                                        socMembList.setEmail(obj.getString("email"));

                                        String membImage = "Pappu";
                                        //membImage = obj.getString("image");
                                        if (membImage.equals("Pappu")){
                                            Resources r = getResources();
                                            float width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, r.getDisplayMetrics());
                                            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, r.getDisplayMetrics());
                                            membImage = "https://placeholdit.imgix.net/~text?txtsize=66&txt=" + width + "%C3%97"+px+"&w=" + width + "&h="+px;
                                        }
                                        socMembList.setImage(membImage);

                                        // adding socMembList to membs array
                                        socMembListList.add(socMembList);
                                    }
                                }
                                else {
                                    RelativeLayout members = (RelativeLayout) findViewById(R.id.socMembersLayout);
                                    members.setVisibility(View.INVISIBLE);
                                }

                            } else if (status.equals("warning")){
                                RelativeLayout members = (RelativeLayout) findViewById(R.id.socMembersLayout);
                                members.setVisibility(View.INVISIBLE);
                                // Now store the user in SQLite
                                String message = jObj.getString("message");

                                Log.d(TAG, message);
                            } else {
                                // Error in login. Get the error message
                                String errorMsg = jObj.getString("message");
                                Toast.makeText(getApplicationContext(),
                                        errorMsg, Toast.LENGTH_LONG).show();
                                RelativeLayout members = (RelativeLayout) findViewById(R.id.socMembersLayout);
                                members.setVisibility(View.INVISIBLE);
                            }
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                            Log.d(TAG, "Json error: " + e.getMessage());
                            RelativeLayout members = (RelativeLayout) findViewById(R.id.socMembersLayout);
                            members.setVisibility(View.INVISIBLE);
                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hidePDialog();
            }

        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(membReq);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SocMembList m = socMembListList.get(position);
                String name = m.getName(),
                        image = m.getImage(),
                        email = m.getEmail();
                int phone = m.getPhone();

                LayoutInflater inflater = LayoutInflater.from(SocDetails.this);
                View dialog_layout = inflater.inflate(R.layout.dialog_society_member, null);

                AlertDialog.Builder dialog = new AlertDialog.Builder(SocDetails.this);
                dialog.setView(dialog_layout);
                dialog.setTitle(name);

                TextView sPhone,sEmail;
                NetworkImageView sImage;

                sPhone = (TextView) dialog_layout.findViewById(R.id.phone);
                sEmail = (TextView) dialog_layout.findViewById(R.id.email);
                sImage = (NetworkImageView) dialog_layout.findViewById(R.id.selectMembImage);

                sImage.setImageUrl(image, imageLoader);
                sEmail.setText(email);
                sPhone.setText("" + phone);

                dialog.setPositiveButton("OK", new
                        DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                dialog.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
