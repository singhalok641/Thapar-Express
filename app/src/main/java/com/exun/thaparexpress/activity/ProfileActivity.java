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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.exun.thaparexpress.Helper.SQLiteHandler;
import com.exun.thaparexpress.R;
import com.mikhaellopez.circularimageview.CircularImageView;

/**
 * Created by root on 10/24/16.
 */
public class ProfileActivity extends AppCompatActivity{

    String name,email,university,roll_number,hostel,room_number,year,phone_number,branch,batch_code;  // Basic details
    //UI elements
    private TextView userDetailsName, userDetailsEmail,userDetailsUniversity,userDetailsRollNumber,userDetailsHostel,userDetailsHostelRoomNumber,userDetailsYear,userDetailsPhoneNumber,userDetailsBranch,userDetailsBatchCode;
    private Toolbar toolbar;
    private CircularImageView _profile;

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
        //university=db.getUserDetails().get("university");
        roll_number=db.getUserDetails().get("roll");
        hostel=db.getUserDetails().get("hostel");
        //room_number=db.getUserDetails().get("room");
        year=db.getUserDetails().get("year");
        phone_number=db.getUserDetails().get("phone");
        branch=db.getUserDetails().get("branch");
        batch_code=db.getUserDetails().get("batch_code");



         //initialsing profile dp
        _profile=(CircularImageView)findViewById(R.id.profile_image);
        // Set Border
        _profile.setBorderColor(getResources().getColor(R.color.GrayLight));
        _profile.setBorderWidth(10);
        // Add Shadow with default param
        //_profile.addShadow();
        // or with custom param
        //_profile.setShadowRadius(15);
        //_profile.setShadowColor(Color.RED);

        //using Glide library to load the profile image from GoogleImageURL
        Glide.with(ProfileActivity.this).load(url)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(_profile);

        _profile.bringToFront();


        initialize();
        setToolbar();
        setTitleOnCollapse();

        setImageOnClickListeners();

    }

    private void setTitleOnCollapse() {
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        collapsingToolbarLayout.setTitle(name);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
    }

    private void initialize() {
        //Details
        userDetailsName = (TextView) findViewById(R.id.userDetailsName);
        userDetailsEmail = (TextView) findViewById(R.id.userDetailsEmail);
        userDetailsUniversity= (TextView) findViewById(R.id.userDetailsUniversity);
        userDetailsRollNumber= (TextView) findViewById(R.id.userDetailsRollNumber);
        userDetailsHostel = (TextView) findViewById(R.id.userDetailsHostel);
        userDetailsHostelRoomNumber= (TextView) findViewById(R.id.userDetailsHostelRoomNumber);
        userDetailsYear= (TextView) findViewById(R.id.userDetailsYear);
        userDetailsPhoneNumber= (TextView) findViewById(R.id.userDetailsPhoneNumber);
        userDetailsBranch= (TextView) findViewById(R.id.userDetailsBranch);
        userDetailsBatchCode=(TextView) findViewById(R.id.userDetailsBatchCode);

        //EditDetails
        fullname= (EditText) findViewById(R.id.reg_fullname);
        reg_email =(EditText) findViewById(R.id.reg_email);

        reg_email.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProfileActivity.this, "Can't edit this field !", Toast.LENGTH_SHORT).show();
            }
        });

        reg_university=(EditText)findViewById(R.id.reg_university);

        //Edit Buttons
        editDetails= (ImageView) findViewById(R.id.userDetailsEditBtn);
        saveDetails = (ImageView) findViewById(R.id.saveUserEdit);
        cancelDetailsEdit = (ImageView) findViewById(R.id.cancelUserEdit);

        //progressBar=(ProgressBar) findViewById(R.id.progress_bar);

        userDetailsName.setText(name);
        userDetailsEmail.setText(email);
        userDetailsUniversity.setText(university);
        userDetailsRollNumber.setText(roll_number);
        userDetailsHostel.setText(hostel);
        userDetailsHostelRoomNumber.setText(room_number);
        userDetailsYear.setText(year);
        userDetailsPhoneNumber.setText(phone_number);
        userDetailsBranch.setText(branch);
        userDetailsBatchCode.setText(batch_code);

        reg_email.setText(email);

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
                userDetailsName.setVisibility(View.GONE);
                userDetailsEmail.setVisibility(View.GONE);
                userDetailsUniversity.setVisibility(View.GONE);

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
                userDetailsName.setVisibility(View.VISIBLE);
                userDetailsEmail.setVisibility(View.VISIBLE);
                userDetailsUniversity.setVisibility(View.VISIBLE);

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

    public boolean validate(final String name, final String email,
                            final String password, final String gender,
                            final String phone, final String branch, final String roll
            ,final String year, final String hostel,final String batch_code,String url) {
        boolean valid = true;

        if (name.isEmpty() || name.length() < 3) {
            fullname.setError("at least 3 characters");
            valid = false;
        } else {
            fullname.setError(null);
        }


        /*
        if (password.isEmpty() || password.length() < 4) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            Toast.makeText(Register.this, "Enter valid password", Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (phone.isEmpty() || phone.length() < 10) {
            inputPhone.setError("at least 10 characters");
            Toast.makeText(Register.this, "Enter valid phone number", Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            inputPhone.setError(null);
        }

        if (gender == null) {
            valid = false;
        }

        if (hostel == null) {
            valid = false;
        }


        if (roll.isEmpty() || roll.length() < 9) {
            inputRoll.setError("at least 9 characters");
            Toast.makeText(Register.this, "Enter a valid roll number", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        else if(roll.length() > 9){
            inputRoll.setError("9 characters only");
            Toast.makeText(Register.this, "Enter a valid roll number", Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            inputRoll.setError(null);
        }

        if (branch.equals("Select branch" )) {
            Toast.makeText(Register.this, "Select Branch !", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        if (batch_code.equals("Select batch" )) {
            Toast.makeText(Register.this, "Select Batch !", Toast.LENGTH_SHORT).show();
            valid = false;
        }

//        else {
//            regBranch.setError(null);
//        }

        if (year.isEmpty()) {
            Toast.makeText(Register.this, "Select year of passing", Toast.LENGTH_SHORT).show();
            valid = false;
        }

*/

        return valid;
    }

}
