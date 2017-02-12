package com.exun.thaparexpress.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.exun.thaparexpress.Helper.BottomNavigationViewHelper;
import com.exun.thaparexpress.Helper.SQLiteHandler;
import com.exun.thaparexpress.R;
import com.exun.thaparexpress.activity.fragments.About;
import com.exun.thaparexpress.activity.fragments.Events;
import com.exun.thaparexpress.activity.fragments.Feedback;
import com.exun.thaparexpress.activity.fragments.Home;
import com.exun.thaparexpress.activity.fragments.Profile;
import com.exun.thaparexpress.activity.fragments.ThaparLogs;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private static String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    boolean doubleBackToExitPressedOnce;
    private int select = 0;
    String branch;
    private SQLiteHandler database;
    String year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SQLiteHandler
        database = new SQLiteHandler(this.getApplicationContext());
        year=database.getUserDetails().get("year");


        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        //Bottom Nav Bar
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        Fragment fragment = null;
                        String title = getString(R.string.app_name);
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                fragment = new Home();
                                title = "Home";
                                break;

                            case R.id.action_profile:
                                fragment = new Profile();
                                title = "Profile";
                                break;

                            case R.id.action_blogs:
                                fragment = new ThaparLogs();
                                title = "Blogs";
                                break;

                            case R.id.action_events:
                                fragment = new Events();
                                title = "Events";
                                break;

                            case R.id.action_notifications:
                                break;

                        }
                        if (fragment != null) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.container_body, fragment);
                            fragmentTransaction.commit();

                            // set the toolbar title
                            getSupportActionBar().setTitle(title);


                        }
                        return true;
                    }
                });


        Intent i = getIntent();

        if (i!=null){
            int post = i.getIntExtra("selectionId",0);
            displayView(post);
        } else {
            // display the first navigation drawer view on app launch
            displayView(0);
        }

    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    public void displayView(int position) {
        Fragment fragment = null;
        Intent i;
        String title = getString(R.string.app_name);
        select = position;
        switch (position) {
            case 0:
                fragment = new Home();
                title = "Home";
                break;
            case 1:
                i = new Intent(MainActivity.this,Societies.class);
                startActivity(i);
                finish();
                break;
            case 2:
                i = new Intent(MainActivity.this,Events.class);
                startActivity(i);
                finish();
                break;

            case 3:
                if(year.equals("2018") || year.equals("2017"))
                {
                    Toast.makeText(MainActivity.this, "Coming Soon :D", Toast.LENGTH_SHORT).show();
                    break;
                }
                else {
                    //Log.e("TimeTable Activity", TAG);
                    i = new Intent(MainActivity.this, TimeTable.class);
                    startActivity(i);
                    i.putExtra("Branch", branch);
                    //Log.e("Check", TAG);
                    finish();
                    break;
                }

            case 4:
                //Toast.makeText(MainActivity.this, "Coming Soon :D", Toast.LENGTH_SHORT).show();
                i = new Intent(MainActivity.this,MessMenu.class);
                startActivity(i);
                finish();
                break;

            case 5:
                fragment = new ThaparLogs();
                title = "ThaparLogs";
                break;

            case 6:
                //Toast.makeText(MainActivity.this, "Coming Soon :D", Toast.LENGTH_SHORT).show();
                i = new Intent(MainActivity.this,Teachers.class);
                startActivity(i);
                finish();
                break;

            case 7:
                //fragment = new Store();
                //title = "Store";
                Toast.makeText(MainActivity.this, "Coming Soon :D", Toast.LENGTH_SHORT).show();
                break;
            //case 6:
            //    Toast.makeText(getApplicationContext(),"Coming soon :D",Toast.LENGTH_SHORT).show();
            //    break;

            case 8:
                Toast.makeText(MainActivity.this, "Coming Soon :D", Toast.LENGTH_SHORT).show();
                //fragment = new Syllabus();
                //title = "Syllabus";
                break;

            case 9:
                fragment = new Feedback();
                title = "Feedback";
                break;
            case 10:
                fragment = new About();
                title = "About";
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }


    @Override
    public void onBackPressed() {
        if (select == 0){
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
        else {
            displayView(0);
        }
    }
}
