package com.exun.thaparexpress.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.exun.thaparexpress.Helper.SQLiteHandler;
import com.exun.thaparexpress.Helper.SessionManager;
import com.exun.thaparexpress.R;
import com.exun.thaparexpress.adapter.AppConfig;
import com.exun.thaparexpress.adapter.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity
{
    private static final String TAG = "LoginActivity";


    EditText _rollText, _passwordText;

    Button _loginButton;
    TextView _signupLink;
    EditText username;
    Toolbar mToolbar;
    ProgressDialog progressDialog;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);


        _rollText = (EditText) findViewById(R.id.input_roll);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _loginButton = (Button) findViewById(R.id.btn_login);
        _signupLink = (TextView) findViewById(R.id.link_signup);
        username = (EditText) findViewById(R.id.input_name);

        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });


        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Sign up activity
                Intent i = new Intent(getApplicationContext(), SignUp.class);
                startActivity(i);
                finish();
            }
        });


    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        final String roll = _rollText.getText().toString();
        final String password = _passwordText.getText().toString();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);

                        Log.d(TAG, "Saving data");
                        String message = jObj.getString("message");
                        Toast.makeText(LoginActivity.this,message,Toast.LENGTH_SHORT).show();

                        JSONObject user = jObj.getJSONObject("user");

                        int id = user.getInt("id");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String gender = user.getString("gender");
                        String phone = user.getString("phone");
                        String branch = user.getString("branch");
                        String roll = user.getString("roll");
                        String year = user.getString("year");
                        String hostel = user.getString("hostel");
                        String batch_code=user.getString("batch_code");
                        String url=user.getString("url");



                        db.addUser(id,name,email,roll,hostel,gender,phone,branch,year,batch_code,url);

                        Log.d(TAG,"Starting main activity");

                        // Launch main activity
                        Intent intent = new Intent(LoginActivity.this,
                                MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(getApplicationContext(),
                                "Login Failed! :/", Toast.LENGTH_SHORT).show();
                        Log.e(TAG,errorMsg);
                        _loginButton.setEnabled(true);

                    }
                } catch (JSONException e) {
                    // JSON error
                    Toast.makeText(getApplicationContext(),
                            "Login Failed! :/", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Login error! Something went wrong.", Toast.LENGTH_SHORT).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "login");
                params.put("roll", roll);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, TAG);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
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

    @Override
    public void onBackPressed() {
        Intent i =new Intent(LoginActivity.this,SignUp.class);
        startActivity(i);
        // disable going bg_splash to the MainActivity
        //moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_SHORT).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String roll = _rollText.getText().toString();
        String password = _passwordText.getText().toString();

        if (roll.isEmpty() || roll.length()<9 ) {
            _rollText.setError("enter a valid roll number");
            valid = false;
        } else {
            _rollText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4) {
            _passwordText.setError("more than 4 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

}
