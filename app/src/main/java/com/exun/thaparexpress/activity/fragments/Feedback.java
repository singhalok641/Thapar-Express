package com.exun.thaparexpress.activity.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.exun.thaparexpress.Helper.SQLiteHandler;
import com.exun.thaparexpress.R;
import com.exun.thaparexpress.adapter.AppConfig;
import com.exun.thaparexpress.adapter.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 10/7/16.
 */



public class Feedback extends Fragment {

    RadioGroup inputFeedback;
    RadioButton radioTypeButton;
    Button _submitFeedback;
    EditText edit_feedback;
    String feedback_type;
    private SQLiteHandler db;

    String id,roll,name,email,phone;

    private ProgressDialog progressDialog;


    int SelectID;

    // Log tag
    private static final String TAG = "Feedback";
    String selection ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feedback, container, false);
        inputFeedback = (RadioGroup) rootView.findViewById(R.id.input_feedback);
        edit_feedback = (EditText) rootView.findViewById(R.id.feedback_box);
        selection = null;


        db = new SQLiteHandler(getActivity().getApplicationContext());

        id=db.getUserDetails().get("id");
        roll=db.getUserDetails().get("roll");
        name=db.getUserDetails().get("name");
        phone=db.getUserDetails().get("phone");
        email=db.getUserDetails().get("email");

        inputFeedback.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.suggestion:
                        selection = "Suggestion";
                        break;
                    case R.id.complain:
                        selection = "Complain";
                        break;
                    case R.id.feature:
                        selection = "Add On Feature";
                        break;
                }
            }
        });

        Log.e(TAG,selection);

    _submitFeedback.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String feedback= edit_feedback.getText().toString();

            submit(roll,id,name,email,phone,selection,feedback);

        }
    });
    return rootView;
    }

    private void submit(final String roll,final String id,final String name,final String email,final String phone,final String selection,final String feedback) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Submitting feedback...");
        progressDialog.show();


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

                    } else {

                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Login Failed! :/", Toast.LENGTH_LONG).show();
                        Log.e(TAG, errorMsg);

                        //login = false;

                    }
                } catch (JSONException e) {
                    // JSON error
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Login Failed! :/", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getActivity().getApplicationContext(),
                        "Login error! Something went wrong.", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        })

        {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to feedback url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "login");
                params.put("id", id);
                params.put("name", name);
                params.put("roll", roll);
                params.put("email", email);
                params.put("type", selection);
                params.put("suggestions",feedback);
                params.put("mobile", phone);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strxReq, TAG);



        }

    private void hideDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
