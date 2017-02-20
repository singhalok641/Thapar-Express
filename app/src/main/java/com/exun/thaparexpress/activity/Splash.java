package com.exun.thaparexpress.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.exun.thaparexpress.Helper.SessionManager;
import com.exun.thaparexpress.R;
import com.exun.thaparexpress.activity.authenticate.SignUp;
import com.github.ybq.android.spinkit.SpinKitView;

/**
 * Created by n00b on 11/25/2015.
 */
public class Splash extends AppCompatActivity {

    private SessionManager session;
    private SpinKitView spinKitView;
    private PackageManager pm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_splash);

        session = new SessionManager(getApplicationContext());

        //pm = this.getPackageManager();
        //spinKitView = (SpinKitView) findViewById(R.id.spin_kit);
        //spinKitView.setIndeterminateDrawable(AppConfig.getLoadingDrawable(this,0,-1));

        //new BackgroundTasks().execute();

        // Session manager
        session = new SessionManager(getApplicationContext());

        if (isOnline()){
            Intent intent = new Intent(this, Authenticate.class);
            startActivity(intent);
            finish();
        }
        else if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent =new Intent(this,TimeTable.class);
            startActivity(intent);
            finish();
            Toast.makeText(Splash.this, "No Internet Connection :/", Toast.LENGTH_SHORT).show();
        }
        else
        {
           new AlertDialog.Builder(Splash.this)
                    .setTitle("No Internet connection!")
                    .setMessage("Please check your connection.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }


    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
