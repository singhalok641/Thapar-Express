package com.exun.thaparexpress.activity.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.exun.thaparexpress.Helper.SessionManager;
import com.exun.thaparexpress.R;
import com.exun.thaparexpress.activity.MainActivity;
import com.exun.thaparexpress.activity.authenticate.LoginActivity;
import com.exun.thaparexpress.activity.authenticate.SignUp;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by root on 2/20/17.
 */

public class Login extends Fragment {

    // Log tag
    private static final String TAG = "Home";

    private SessionManager session;
    ProgressDialog progressDialog;
    EditText et_email, et_password;
    Button btn_server_login;

    public Login() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        // Session manager
        session = new SessionManager(getApplicationContext());
        et_email = (EditText) rootView.findViewById(R.id.et_email);
        et_password = (EditText) rootView.findViewById(R.id.et_password);
        btn_server_login = (Button) rootView.findViewById(R.id.btn_server_login);
        // Check if user is already logged in or not

        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }

        btn_server_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        return rootView;
    }

    public void login() {
        Log.d(TAG, "Login");

    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        Log.e(TAG,"hide");
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public void onLoginSuccess() {
        btn_server_login.setEnabled(true);

    }

    public void onLoginFailed() {
        Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();

        btn_server_login.setEnabled(true);
    }

}
