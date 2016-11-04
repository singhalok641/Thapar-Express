package com.exun.thaparexpress.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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

public class Register extends AppCompatActivity  {

    private static final String TAG = "Register";

    EditText _nameText,_emailText, _passwordText, inputPhone, inputRoll;
    RadioGroup inputGender, inputHosteMale, inputHostelFemale;
    Spinner spinner,spinner_university,spinner_2018_branch,spinner_2018_batch,spinner_2019_branch,spinner_2019_batch,spinner_2020_branch,spinner_2020_batch_1,spinner_2020_batch_2;
    private ProgressDialog pDialog;
    Button _signupButton;
    TextView _loginLink;
    AutoCompleteTextView regBranch;
    AutoCompleteTextView regCourse;
    String gender=null,hostel=null,selection=null,year=null,branch=null,batch_1=null,batch_2=null,batch_code=null,university=null;
    RadioButton radioSexButton,radioHostelButton;
    int selectHID;
    private SessionManager session;
    private SQLiteHandler db;
    LinearLayout hostelMale, hostelFemale;
    LinearLayout year_18,year_19,year_20;
    RelativeLayout rl;
    String google_username;
    String google_user_email;
    String url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        _emailText = (EditText) findViewById(R.id.input_email);
        _nameText = (EditText) findViewById(R.id.input_name);
        _passwordText = (EditText) findViewById(R.id.input_password);
        //inputBranch = (EditText) findViewById(R.id.input_branch);
        inputRoll = (EditText) findViewById(R.id.reg_roll);
//        inputYear = (EditText) findViewById(R.id.reg_year);
        inputPhone = (EditText) findViewById(R.id.reg_phone);
        inputGender = (RadioGroup) findViewById(R.id.reg_gender);
        inputHostelFemale = (RadioGroup) findViewById(R.id.reg_hostel_female);
        inputHosteMale = (RadioGroup) findViewById(R.id.reg_hostel_male);
        _signupButton = (Button) findViewById(R.id.btn_signup);
        _loginLink = (TextView) findViewById(R.id.link_login);
        hostelMale = (LinearLayout) findViewById(R.id.hostel_male);
        hostelFemale = (LinearLayout) findViewById(R.id.hostel_female);

        year_18=(LinearLayout) findViewById(R.id.year_18);
        year_19=(LinearLayout) findViewById(R.id.year_19);
        year_20=(LinearLayout) findViewById(R.id.year_20);

        //Spinner for University
        spinner_university = (Spinner) findViewById(R.id.University);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> university_adapter = ArrayAdapter.createFromResource(this,
                R.array.university, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        university_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_university.setAdapter(university_adapter);


        //Spinner for graduation year
        spinner = (Spinner) findViewById(R.id.year);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> year_adapter = ArrayAdapter.createFromResource(this,
                R.array.year, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        year_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(year_adapter);

        //Spinner for year 2018
        //Branch
        spinner_2018_branch = (Spinner) findViewById(R.id.year_2018_branch);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> _2018_branch_adapter = ArrayAdapter.createFromResource(this,
                R.array.branch, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        _2018_branch_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_2018_branch.setAdapter(_2018_branch_adapter);

        //Spinner for year 2018
        //Batch
        spinner_2018_batch = (Spinner) findViewById(R.id.year_2018_batch);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> _2018_batch_adapter = ArrayAdapter.createFromResource(this,
                R.array.batch, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        _2018_batch_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_2018_batch.setAdapter(_2018_batch_adapter);

        //Spinner for year 2019
        //branch
        spinner_2019_branch = (Spinner) findViewById(R.id.year_2019_branch);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> _2019_branch_adapter = ArrayAdapter.createFromResource(this,
                R.array.branch, R.layout.spinner_item);
        // Specify the layout to use when the lis of choices appears
        _2019_branch_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_2019_branch.setAdapter(_2019_branch_adapter);

        //Spinner for year 2019
        //Batch
        spinner_2019_batch = (Spinner) findViewById(R.id.year_2019_batch);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> _2019_batch_adapter = ArrayAdapter.createFromResource(this,
                R.array.batch, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        _2019_batch_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_2019_batch.setAdapter(_2019_batch_adapter);

        //Spinner for year 2018
        //branch
        spinner_2020_branch = (Spinner) findViewById(R.id.year_2020_branch);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> _2020_branch_adapter = ArrayAdapter.createFromResource(this,
                R.array.branch, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        _2020_branch_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_2020_branch.setAdapter(_2020_branch_adapter);

        //Batch
        spinner_2020_batch_1 = (Spinner) findViewById(R.id.year_2020_batch_1);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> _2020_batch_1_adapter = ArrayAdapter.createFromResource(this,
                R.array._2020_batch_1, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        _2020_batch_1_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_2020_batch_1.setAdapter(_2020_batch_1_adapter);

        //Batch
        spinner_2020_batch_2 = (Spinner) findViewById(R.id.year_2020_batch_2);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> _2020_batch_2_adapter = ArrayAdapter.createFromResource(this,
                R.array._2020_batch_2, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        _2020_batch_2_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_2020_batch_2.setAdapter(_2020_batch_2_adapter);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setIndeterminate(true);


        //GettingBundle data from google sign in
        if(getIntent().getExtras()!=null) {
            Bundle args = getIntent().getExtras();
            google_username= args.getString("user_name");
            google_user_email = args.getString("user_id");
            url=args.getString("user_photo");

        }
        //Setting name and email in the fields
        _nameText.setText(google_username);
        Log.e(TAG,"username set:"+google_username);
        Log.e(TAG,"useremail set:"+google_user_email);
        Log.e(TAG,"userphoto set:"+url);
        _emailText.setText(google_user_email);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(Register.this,
                    MainActivity.class);
            startActivity(intent);
            finish();
        }

        inputGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.male:
                        hostelMale.setVisibility(View.VISIBLE);
                        hostelFemale.setVisibility(View.GONE);
                        selection = "male";
                        break;
                    case R.id.female:
                        hostelMale.setVisibility(View.GONE);
                        hostelFemale.setVisibility(View.VISIBLE);
                        selection = "female";
                        break;
                }
            }
        });

        spinner_university.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                switch(position)
                {
                    case 0:
                        university=parent.getItemAtPosition(position).toString();;
                    case 1:
                        university = parent.getItemAtPosition(position).toString();
                    case 2:
                        university = parent.getItemAtPosition(position).toString();
                    case 3:
                        university= parent.getItemAtPosition(position).toString();

                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //ItemSelectedListener for
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //On selecting a spinner item
                switch (position) {
                    case 0:
                        year = parent.getItemAtPosition(position).toString();
                    case 1:
                        year = parent.getItemAtPosition(position).toString();
                    case 2:
                        year = parent.getItemAtPosition(position).toString();
                    case 3:
                        year= parent.getItemAtPosition(position).toString();


                        break;

                    // Showing selected spinner item
                    //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                }

                collapsingLayout(year);

                Log.e(TAG, year);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });

        //ItemSelectedListener for
        spinner_2018_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //On selecting a spinner item
                switch (position) {
                    case 0:
                        branch = parent.getItemAtPosition(position).toString();
                    case 1:
                        branch = parent.getItemAtPosition(position).toString();
                    case 2:
                        branch = parent.getItemAtPosition(position).toString();
                    case 3:
                        branch = parent.getItemAtPosition(position).toString();
                    case 4:
                        branch = parent.getItemAtPosition(position).toString();
                    case 5:
                        branch = parent.getItemAtPosition(position).toString();
                    case 6:
                        branch = parent.getItemAtPosition(position).toString();
                    case 7:
                        branch = parent.getItemAtPosition(position).toString();
                    case 8:
                        branch = parent.getItemAtPosition(position).toString();
                    case 9:
                        branch = parent.getItemAtPosition(position).toString();
                    case 10:
                        branch = parent.getItemAtPosition(position).toString();
                    case 11:
                        branch = parent.getItemAtPosition(position).toString();
                    case 12:
                        branch = parent.getItemAtPosition(position).toString();
                    case 13:
                        branch = parent.getItemAtPosition(position).toString();
                    case 14:
                        branch = parent.getItemAtPosition(position).toString();
                        break;

                        // Showing selected spinner item
                        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                }


                // Showing selected spinner item
                //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });

        //ItemSelectedListener for
        spinner_2019_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //On selecting a spinner item
                switch (position) {
                    case 0:
                        branch = parent.getItemAtPosition(position).toString();
                    case 1:
                        branch = parent.getItemAtPosition(position).toString();
                    case 2:
                        branch = parent.getItemAtPosition(position).toString();
                    case 3:
                        branch = parent.getItemAtPosition(position).toString();
                    case 4:
                        branch = parent.getItemAtPosition(position).toString();
                    case 5:
                        branch = parent.getItemAtPosition(position).toString();
                    case 6:
                        branch = parent.getItemAtPosition(position).toString();
                    case 7:
                        branch = parent.getItemAtPosition(position).toString();
                    case 8:
                        branch = parent.getItemAtPosition(position).toString();
                    case 9:
                        branch = parent.getItemAtPosition(position).toString();
                    case 10:
                        branch = parent.getItemAtPosition(position).toString();
                    case 11:
                        branch = parent.getItemAtPosition(position).toString();
                    case 12:
                        branch = parent.getItemAtPosition(position).toString();
                    case 13:
                        branch = parent.getItemAtPosition(position).toString();
                    case 14:
                        branch = parent.getItemAtPosition(position).toString();

                        break;



                        // Showing selected spinner item
                    //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });
        //ItemSelectedListener for
        spinner_2020_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //On selecting a spinner item
                switch (position) {
                    case 0:
                        branch = parent.getItemAtPosition(position).toString();
                    case 1:
                        branch = parent.getItemAtPosition(position).toString();
                    case 2:
                        branch = parent.getItemAtPosition(position).toString();
                    case 3:
                        branch = parent.getItemAtPosition(position).toString();
                    case 4:
                        branch = parent.getItemAtPosition(position).toString();
                    case 5:
                        branch = parent.getItemAtPosition(position).toString();
                    case 6:
                        branch = parent.getItemAtPosition(position).toString();
                    case 7:
                        branch = parent.getItemAtPosition(position).toString();
                    case 8:
                        branch = parent.getItemAtPosition(position).toString();
                    case 9:
                        branch = parent.getItemAtPosition(position).toString();
                    case 10:
                        branch = parent.getItemAtPosition(position).toString();
                    case 11:
                        branch = parent.getItemAtPosition(position).toString();
                    case 12:
                        branch = parent.getItemAtPosition(position).toString();
                    case 13:
                        branch = parent.getItemAtPosition(position).toString();
                    case 14:
                        branch = parent.getItemAtPosition(position).toString();

                        break;

                        // Showing selected spinner item
                    //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });

        //ItemSelectedListener for
        spinner_2018_batch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //On selecting a spinner item
                switch (position) {
                    case 0:
                        batch_code = parent.getItemAtPosition(position).toString();
                    case 1:
                        batch_code = parent.getItemAtPosition(position).toString();
                    case 2:
                        batch_code = parent.getItemAtPosition(position).toString();
                    case 3:
                        batch_code = parent.getItemAtPosition(position).toString();
                    case 4:
                        batch_code = parent.getItemAtPosition(position).toString();
                    case 5:
                        batch_code = parent.getItemAtPosition(position).toString();
                    case 6:
                        batch_code = parent.getItemAtPosition(position).toString();
                    case 7:
                        batch_code = parent.getItemAtPosition(position).toString();
                    case 8:
                        batch_code = parent.getItemAtPosition(position).toString();
                    case 9:
                        batch_code = parent.getItemAtPosition(position).toString();
                    case 10:
                        batch_code = parent.getItemAtPosition(position).toString();


                        break;
                        // Showing selected spinner item
                        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                }


                // Showing selected spinner item
                //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });

        //ItemSelectedListener for
        spinner_2019_batch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //On selecting a spinner item
                switch (position) {
                    case 0:
                        batch_code = parent.getItemAtPosition(position).toString();
                    case 1:
                        batch_code = parent.getItemAtPosition(position).toString();
                    case 2:
                        batch_code = parent.getItemAtPosition(position).toString();
                    case 3:
                        batch_code = parent.getItemAtPosition(position).toString();
                    case 4:
                        batch_code = parent.getItemAtPosition(position).toString();
                    case 5:
                        batch_code = parent.getItemAtPosition(position).toString();
                    case 6:
                        batch_code = parent.getItemAtPosition(position).toString();
                    case 7:
                        batch_code = parent.getItemAtPosition(position).toString();
                    case 8:
                        batch_code = parent.getItemAtPosition(position).toString();
                    case 9:
                        batch_code = parent.getItemAtPosition(position).toString();
                    case 10:
                        batch_code = parent.getItemAtPosition(position).toString();


                        break;
                        // Showing selected spinner item
                        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                }



                // Showing selected spinner item
                //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });

        //ItemSelectedListener for
        spinner_2020_batch_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //On selecting a spinner item
                switch (position) {
                    case 0:
                        batch_1 = parent.getItemAtPosition(position).toString();
                    case 1:
                        batch_1 = parent.getItemAtPosition(position).toString();
                    case 2:
                        batch_1 = parent.getItemAtPosition(position).toString();
                    case 3:
                        batch_1 = parent.getItemAtPosition(position).toString();
                    case 4:
                        batch_1 = parent.getItemAtPosition(position).toString();
                    case 5:
                        batch_1 = parent.getItemAtPosition(position).toString();
                    case 6:
                        batch_1 = parent.getItemAtPosition(position).toString();
                    case 7:
                        batch_1 = parent.getItemAtPosition(position).toString();
                    case 8:
                        batch_1 = parent.getItemAtPosition(position).toString();
                    case 9:
                        batch_1 = parent.getItemAtPosition(position).toString();
                    case 10:
                        batch_1 = parent.getItemAtPosition(position).toString();
                    case 11:
                        batch_1 =parent.getItemAtPosition(position).toString();
                    case 12:
                        batch_1 = parent.getItemAtPosition(position).toString();
                    case 13:
                        batch_1 = parent.getItemAtPosition(position).toString();
                    case 14:
                        batch_1 = parent.getItemAtPosition(position).toString();
                    case 15:
                        batch_1 = parent.getItemAtPosition(position).toString();
                    case 16:
                        batch_1 = parent.getItemAtPosition(position).toString();
                    case 17:
                        batch_1 = parent.getItemAtPosition(position).toString();
                    case 18:
                        batch_1 = parent.getItemAtPosition(position).toString();


                        break;

                        // Showing selected spinner item
                        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                }

                batch_code =batch_1 + batch_2;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });

        //ItemSelectedListener for
        spinner_2020_batch_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        batch_2 = parent.getItemAtPosition(position).toString();
                    case 1:
                        batch_2 = parent.getItemAtPosition(position).toString();
                    case 2:
                        batch_2 = parent.getItemAtPosition(position).toString();
                    case 3:
                        batch_2 = parent.getItemAtPosition(position).toString();


                        break;
                        // Showing selected spinner item
                        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                }
                batch_code =batch_1 + batch_2;

            }





            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });



        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = _nameText.getText().toString();
                String email = _emailText.getText().toString();
                String password = _passwordText.getText().toString();
                String phone = inputPhone.getText().toString();
                String roll = inputRoll.getText().toString();

                int selectID = inputGender.getCheckedRadioButtonId();

                if (selectID!= -1){
                    radioSexButton = (RadioButton) inputGender.findViewById(selectID);
                    gender = radioSexButton.getText().toString();
                } else
                {
                    gender = null;
                }

                if (!(inputGender.getCheckedRadioButtonId() == -1)){
                    if (selection.equals("male"))
                        selectHID = inputHosteMale.getCheckedRadioButtonId();
                    else
                        selectHID = inputHostelFemale.getCheckedRadioButtonId();

                    if (selectHID!= -1){
                        if (selection.equals("male"))
                            radioHostelButton = (RadioButton) inputHosteMale.findViewById(selectHID);
                        else
                            radioHostelButton = (RadioButton) inputHostelFemale.findViewById(selectHID);
                        hostel = radioHostelButton.getText().toString();
                    } else {
                        hostel = null;
                    }
                }
                else {
                    hostel = null;
                }


                Log.e(TAG,"Batch_code"+" "+batch_code);
                Log.e(TAG,"Branch"+" "+branch);
                Log.e(TAG,"URL"+" "+url);



                if(year.equals("2020")) {
                    signup(name, email, password, gender, phone, branch, roll, year, hostel, url, batch_code);
                }
                else
                {
                    signup(name, email, password, gender, phone, branch, roll, year, hostel, url, branch+batch_code);

                }

            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void collapsingLayout(String item) {
        if (item.equals("2018")) {
            year_20.setVisibility(View.GONE);
            year_18.setVisibility(View.VISIBLE);
            year_19.setVisibility(View.GONE);
            //Toast.makeText(Register.this, "Selected: 2018", Toast.LENGTH_SHORT).show();
        }
        if (item.equals("2019")) {
            year_20.setVisibility(View.GONE);
            year_18.setVisibility(View.GONE);
            year_19.setVisibility(View.VISIBLE);
            //Toast.makeText(Register.this, "Selected: 2019", Toast.LENGTH_SHORT).show();

        }
        if (item.equals("2020")) {
            year_18.setVisibility(View.GONE);
            year_19.setVisibility(View.GONE);
            year_20.setVisibility(View.VISIBLE);
            //Toast.makeText(Register.this, "Selected: 2019", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     */
    public void signup(final String name, final String email,
                       final String password, final String gender,
                       final String phone, final String branch, final String roll
                        ,final String year, final String hostel,final String url,final String batch_code) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";


        if (!validate(name, email, password, gender, phone, branch, roll, year, hostel,batch_code,url)) {
            onSignupFailed();
            return;
        }

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        session.setLogin(true);

                        Log.d(TAG, "Saving data");
                        Toast.makeText(Register.this,jObj.getString("message"),Toast.LENGTH_SHORT).show();
                        int id = jObj.getInt("id");

                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                             db.addUser(id,name, email,roll,hostel,gender,phone,branch,year,batch_code,url);

                        // Launch main activity
                        Intent intent = new Intent(
                                Register.this,
                                MainActivity.class);
                        startActivity(intent);
                        finish();


                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                        Log.e(TAG,errorMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Registration error! Something went wrong", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {


            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "register");
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("gender", gender);
                params.put("phone", phone);
                params.put("branch", branch);
                params.put("roll", roll);
                params.put("year", year);
                params.put("hostel", hostel);
                params.put("batch_code", batch_code);
                params.put("url",url);

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Register failed", Toast.LENGTH_LONG).show();


        _signupButton.setEnabled(true);
    }

    public boolean validate(final String name, final String email,
                            final String password, final String gender,
                            final String phone, final String branch, final String roll
                            ,final String year, final String hostel,final String batch_code,String url) {
        boolean valid = true;

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() || !email.equals(google_user_email)) {
            _emailText.setError("enter a correct email address");
            Toast.makeText(Register.this, "enter a correct email address", Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            _emailText.setError(null);
        }

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


        return valid;
    }

}
