package com.exun.thaparexpress.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.exun.thaparexpress.Helper.SQLiteHandler;
import com.exun.thaparexpress.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by root on 10/24/16.
 */
public class ProfileActivity extends AppCompatActivity {

    String name,email;  // Basic details
    //UI elements
    private TextView userName, userEmail,userUniversity;
    private Toolbar toolbar;
    private CircleImageView _profile;

    // SQLite database
    private SQLiteHandler db;
    //Strings
    private String url;
    //Edit Button
    private ImageView editDetails;
    private ImageView saveDetails;
    private ImageView cancelDetailsEdit;
    //EditText
    private EditText fullname,reg_email,reg_university;
    //ProgressBar
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // SQLite database handler
        db = new SQLiteHandler(ProfileActivity.this.getApplicationContext());

        url=db.getUserDetails().get("url");
        name = db.getUserDetails().get("name");
        email = db.getUserDetails().get("email");

         //initialsing profile dp
        _profile=(CircleImageView)findViewById(R.id.profile_image);
        //using Glide library to load the profile image from GoogleImageURL
        Glide.with(ProfileActivity.this).load(url)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(_profile);

        initialize();
        setToolbar();
        setTitleOnCollapse();

        setImageOnClickListeners();

    }

    private void setTitleOnCollapse() {
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        collapsingToolbarLayout.setTitle("User Profile");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
    }

    private void initialize() {
        //Details
        userName = (TextView) findViewById(R.id.userDetailsName);
        userEmail = (TextView) findViewById(R.id.userDetailsEmail);
        userUniversity= (TextView) findViewById(R.id.userDetailsUniversity);

        //EditDetails
        fullname= (EditText) findViewById(R.id.reg_fullname);
        reg_email =(EditText) findViewById(R.id.reg_email);
        reg_university=(EditText)findViewById(R.id.reg_university);

        //Edit Buttons
        editDetails= (ImageView) findViewById(R.id.userDetailsEditBtn);
        saveDetails = (ImageView) findViewById(R.id.saveUserEdit);
        cancelDetailsEdit = (ImageView) findViewById(R.id.cancelUserEdit);

        //progressBar=(ProgressBar) findViewById(R.id.progress_bar);

        userName.setText(name);
        userEmail.setText(email);
    }

    private void setImageOnClickListeners() {
        editDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Buttons
                editDetails.setVisibility(View.GONE);
                saveDetails.setVisibility(View.VISIBLE);
                cancelDetailsEdit.setVisibility(View.VISIBLE);

                //Details
                userName.setVisibility(View.GONE);
                userEmail.setVisibility(View.GONE);
                userUniversity.setVisibility(View.GONE);

                //Edit Details
                fullname.setVisibility(View.VISIBLE);
                reg_email.setVisibility(View.VISIBLE);
                reg_university.setVisibility(View.VISIBLE);
                //progressBar.setVisibility(View.VISIBLE);
            }
        });

        saveDetails.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                String username=fullname.getText().toString();
                String useremail=reg_email.getText().toString();
                String university=reg_university.getText().toString();



            }
        });

        cancelDetailsEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Buttons
                editDetails.setVisibility(View.VISIBLE);
                saveDetails.setVisibility(View.GONE);
                cancelDetailsEdit.setVisibility(View.GONE);

                //Details
                userName.setVisibility(View.VISIBLE);
                userEmail.setVisibility(View.VISIBLE);
                userUniversity.setVisibility(View.VISIBLE);

                //Edit Details
                fullname.setVisibility(View.GONE);
                reg_email.setVisibility(View.GONE);
                reg_university.setVisibility(View.GONE);
            }
        });



        }

    private void setToolbar() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

}
