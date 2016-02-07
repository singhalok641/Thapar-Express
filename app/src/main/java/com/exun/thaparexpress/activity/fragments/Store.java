package com.exun.thaparexpress.activity.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.exun.thaparexpress.Helper.SQLiteHandler;
import com.exun.thaparexpress.R;
import com.exun.thaparexpress.adapter.AppConfig;
import com.exun.thaparexpress.adapter.AppController;
import com.exun.thaparexpress.adapter.RVStoreAdapter;
import com.exun.thaparexpress.model.StoreList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by n00b on 11/25/2015.
 */
public class Store extends Fragment{

    // Log tag
    private static final String TAG ="Store";

    // blogs json url
    private static final String url = AppConfig.URL_STORE_LIST;
    private ProgressDialog pDialog;
    private List<StoreList> storeList = new ArrayList<StoreList>();

    //New
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    String sName, sPrice, sCond, sCat;
    EditText name, price;
    Spinner spinner, spinnerCond;
    private ProgressDialog progressDialog;
    private SQLiteHandler db;
    Boolean login = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_store, container, false);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                View dialog_layout = inflater.inflate(R.layout.dialog_add_store_items, null);

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(),R.style.YourAlertDialogTheme);
                dialog.setView(dialog_layout);
                dialog.setTitle("Add an item for sale");

//                int textViewId = dialog.getContext().getResources().getIdentifier("android:id/alertTitle", null, null);
//                TextView tv = (TextView) dialog.findViewById(textViewId);
//                tv.setTextColor(getResources().getColor(R.color.textColorPrimary));


                name = (EditText) dialog_layout.findViewById(R.id.input_name);
                price = (EditText) dialog_layout.findViewById(R.id.input_price);
                spinner = (Spinner) dialog_layout.findViewById(R.id.spin);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                        R.array.spinner_cat, R.layout.spinner_list_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                spinnerCond = (Spinner) dialog_layout.findViewById(R.id.spinCond);
                ArrayAdapter<CharSequence> adapterCond = ArrayAdapter.createFromResource(getActivity(),
                        R.array.spinner_cond, R.layout.spinner_list_item);
                adapterCond.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCond.setAdapter(adapterCond);

                dialog.setPositiveButton("OK", new
                        DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                sName = name.getText().toString();
                                sPrice = price.getText().toString();
                                sCond = spinnerCond.getSelectedItem().toString();
                                sCat = spinner.getSelectedItem().toString();

                                if (valid(sName, sPrice)) {

                                    if (add(sName, sPrice, sCat, sCond)) {
                                        dialog.dismiss();
                                    }
                                } else {
                                    Toast.makeText(getActivity(), "Please add the required data", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = dialog.create();
                alert.show();
                Button pButton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
                pButton.setTextColor(Color.WHITE);
                Button nButton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
                nButton.setTextColor(Color.WHITE);
            }
        });

        db = new SQLiteHandler(getActivity().getApplicationContext());

        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.store_rv);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RVStoreAdapter(storeList);
        mRecyclerView.setAdapter(mAdapter);

        // Creating volley request obj
        StringRequest storeReq = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Store: " + response);
                        hidePDialog();

                        try {
                            JSONObject jObj = new JSONObject(response);
                            String status = jObj.getString("status");

                            // Check for error node in json
                            if (status.equals("success")) {

                                // Now store the user in SQLite
                                String message = jObj.getString("message");

                                Log.d(TAG, "Store " + message);

                                JSONArray data = jObj.getJSONArray("data");

                                if (data.length() > 0){

                                    for (int k = 0; k< data.length();k++){

                                        JSONObject obj = data.getJSONObject(k);
                                        StoreList store = new StoreList();
                                        store.setName(obj.getString("item"));
                                        store.setId(obj.getInt("id"));
                                        store.setTimer(obj.getString("timer"));
                                        store.setCat(obj.getString("cat"));
                                        store.setCondition(obj.getString("cond"));
                                        store.setUserid(obj.getInt("userid"));
                                        store.setPrice(obj.getString("price"));
                                        store.setsEmail(obj.getString("email"));
                                        store.setsName(obj.getString("name"));
                                        store.setsRoll(obj.getString("roll"));
                                        store.setsPhone(obj.getString("phone"));

                                        // adding blog to blogs array
                                        storeList.add(store);
                                    }
                                }

                            } else {
                                // Error in login. Get the error message
                                String errorMsg = jObj.getString("message");
                                Toast.makeText(getActivity(),
                                        "Connection failed! :(", Toast.LENGTH_SHORT).show();
                                Log.d(TAG,errorMsg);
                            }
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                            Toast.makeText(getActivity(),
                                    "Connection failed! :(", Toast.LENGTH_SHORT).show();
                            Log.d(TAG,"Json error: " + e.getMessage());
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
                        "Connection failed! :(", Toast.LENGTH_SHORT).show();
                hidePDialog();
            }

        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(storeReq);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((RVStoreAdapter) mAdapter).setOnItemClickListener(new RVStoreAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                StoreList sl = storeList.get(position);
                String sPhone,sEmail,sName,sRoll;

                sPhone= sl.getsPhone();
                sEmail = sl.getsEmail();
                sName = sl.getsName();
                sRoll = sl.getsRoll();

                LayoutInflater inflater = LayoutInflater.from(getActivity());
                View dialog_layout = inflater.inflate(R.layout.dialog_store_items, null);

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setView(dialog_layout);
                dialog.setTitle("Seller details");

                TextView email,phone,name,roll;

                phone = (TextView) dialog_layout.findViewById(R.id.phone);
                email = (TextView) dialog_layout.findViewById(R.id.email);
                name = (TextView) dialog_layout.findViewById(R.id.name);
                roll = (TextView) dialog_layout.findViewById(R.id.roll);


                email.setText(sEmail);
                name.setText(sName);
                roll.setText(sRoll);
                phone.setText("" + sPhone);

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
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
        hideDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    private void hideDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public boolean valid(String name, String price){

        if (name.isEmpty()){
            return false;
        }

        if (price.isEmpty()){
            return false;
        }

        return true;
    }

    public boolean add(final String asname, final String asprice, final String ascat, final String ascond){


        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Adding...");
        progressDialog.show();

        final String userid = db.getUserDetails().get("id");

        StringRequest strxReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ADD_STORE_ITEM, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {

                        Log.d(TAG, "Saving data");
                        String message = jObj.getString("message");
                        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();

                        login = true;
                    } else {

                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Login Failed! :/", Toast.LENGTH_LONG).show();
                        Log.e(TAG, errorMsg);

                        login = false;

                    }
                } catch (JSONException e) {
                    // JSON error
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Login Failed! :/", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                    login = false;
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getActivity().getApplicationContext(),
                        "Login error! Something went wrong.", Toast.LENGTH_LONG).show();
                hideDialog();
                login = false;
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "login");
                params.put("item", asname);
                params.put("cat", ascat);
                params.put("cond", ascond);
                params.put("price", asprice);
                params.put("userid", userid);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strxReq, TAG);

        return login;
    }

    class CustomListener implements DialogInterface.OnClickListener {
        private final Dialog dialog;
        public CustomListener(Dialog dialog) {
            this.dialog = dialog;
        }
        @Override
        public void onClick(DialogInterface dialog, int which) {
            sName = name .getText().toString();
            sPrice = price.getText().toString();
            sCond = spinnerCond.getSelectedItem().toString();
            sCat= spinner.getSelectedItem().toString();

            if (valid(sName,sPrice)){

                if (add(sName,sPrice,sCat,sCond)){
                    dialog.dismiss();
                }
            } else {
                Toast.makeText(getActivity(),"Please add the required data",Toast.LENGTH_SHORT).show();
            }

        }
    }
}
