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
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.exun.thaparexpress.Helper.SQLiteHandler;
import com.exun.thaparexpress.R;
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
    Button _submitFeedback;
    EditText edit_feedback;
    private SQLiteHandler db;
    String id,roll,name,email,phone;
    private ProgressDialog progressDialog;
    final String url ="http://thapar.brinjal.in/api/v1/feedback";

    // Log tag
    private static final String TAG = "Feedback";
    String selection ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feedback, container, false);
        inputFeedback = (RadioGroup) rootView.findViewById(R.id.input_feedback);
        edit_feedback = (EditText) rootView.findViewById(R.id.feedback_box);
        _submitFeedback=(Button) rootView.findViewById(R.id.btn_login);
        inputFeedback.check(R.id.suggestion);
        selection = null;

        db = new SQLiteHandler(getActivity().getApplicationContext());
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

    _submitFeedback.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String feedback= edit_feedback.getText().toString();

            Map<String, String> params = new HashMap<String, String>();
            params.put("roll", roll);
            params.put("name", name);
            params.put("email", email);
            params.put("type", selection);
            params.put("suggestions",feedback);
            params.put("mobile", phone);

            submit(params);
        }
    });

    return rootView;
    }

    private void submit(Map<String, String> params) {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Submitting feedback...");
        progressDialog.show();




        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        Log.d("Response: Description ", response.toString());

                        try {
                            String status = response.getString("status");
                            String message = response.getString("message");

                            Log.d(TAG, "onResponse: " + message);
                            if (status.equals("success")) {

                                Toast.makeText(getActivity(), "Thank you for your feedback :)", Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Connection failed :/", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        hideDialog();
                        Log.d("Error.Response", error.toString());
                        Toast.makeText(getActivity(), "Connection failed :/", Toast.LENGTH_SHORT).show();
                    }
                });

        request.setRetryPolicy(new DefaultRetryPolicy(10000, 3, 2));
        AppController.getInstance().addToRequestQueue(request);
    }

    private void hideDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
