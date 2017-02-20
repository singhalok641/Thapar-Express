package com.exun.thaparexpress.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.exun.thaparexpress.R;
import com.exun.thaparexpress.activity.fragments.Login;

/**
 * Created by root on 2/12/17.
 */

public class Authenticate extends AppCompatActivity{
    private static String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    boolean doubleBackToExitPressedOnce;
    private int select = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate);

        Fragment fragment = new Login();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        // disable going bg_splash to the MainActivity
        moveTaskToBack(true);

    }
}


