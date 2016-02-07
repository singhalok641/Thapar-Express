package com.exun.thaparexpress.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
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

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private static final String TAG = "SignupActivity";

    EditText _nameText,_emailText, _passwordText, inputPhone, inputRoll;
    RadioGroup inputGender, inputHosteMale, inputHostelFemale;
    private ProgressDialog pDialog;
    Button _signupButton;
    TextView _loginLink;
    AutoCompleteTextView regBranch;
    String gender=null,hostel=null,selection=null,year = ""+2016;
    RadioButton radioSexButton,radioHostelButton;
    int selectHID;
    private SessionManager session;
    private SQLiteHandler db;
    LinearLayout hostelMale, hostelFemale;
    NumberPicker np;
    RelativeLayout rl;
    String branches[] = {"CHE","CIE","COE","CAG","CML","SOE","EIC","ECE","ELE","MEE","MPE","MTX","BTE","ECM"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        _nameText = (EditText) findViewById(R.id.input_name);
        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
//        inputBranch = (EditText) findViewById(R.id.input_branch);
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
        regBranch = (AutoCompleteTextView) findViewById(R.id.input_branch);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, branches);
        regBranch.setThreshold(1);
        regBranch.setAdapter(adapter);
        np = (NumberPicker) findViewById(R.id.reg_year);
        np.setMinValue(2016);
        np.setMaxValue(2020);
        np.setWrapSelectorWheel(true);
        Resources resources = getResources();
        int npColor = resources.getColor(R.color.list_row_end_color);
        setNumberPickerTextColor(np, npColor);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setIndeterminate(true);

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

        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                year = "" + newVal;
            }
        });

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = _nameText.getText().toString();
                String email = _emailText.getText().toString();
                String password = _passwordText.getText().toString();
                String phone = inputPhone.getText().toString();
                String branch = regBranch.getText().toString();
                String roll = inputRoll.getText().toString();

                int selectID = inputGender.getCheckedRadioButtonId();
                if (selectID!= -1){
                    radioSexButton = (RadioButton) inputGender.findViewById(selectID);
                    gender = radioSexButton.getText().toString();
                } else {
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

                signup(name, email, password, gender, phone, branch, roll, year, hostel);
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

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     */
    public void signup(final String name, final String email,
                       final String password, final String gender,
                       final String phone, final String branch, final String roll
                        ,final String year, final String hostel) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        if (!validate(name, email, password, gender, phone, branch, roll, year, hostel)) {
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
                        db.addUser(id,name, email,roll,hostel,gender,phone,branch,year );

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
                            ,final String year, final String hostel) {
        boolean valid = true;

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (phone.isEmpty() || phone.length() < 10) {
            inputPhone.setError("at least 10 characters");
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
            valid = false;
        }
        else if(roll.length() > 9){
            inputRoll.setError("9 characters only");
            valid = false;
        } else {
            inputRoll.setError(null);
        }

        if (branch.isEmpty() || branch.length() < 3 ) {
            regBranch.setError("enter 3 characters");
            valid = false;
        }
        else if(branch.length() > 3){
            regBranch.setError("3 characters only");
            valid = false;
        }
//        else {
//            regBranch.setError(null);
//        }

        if (year.isEmpty()) {
            valid = false;
        }

        return valid;
    }

    public static boolean setNumberPickerTextColor(NumberPicker numberPicker, int color)
    {
        final int count = numberPicker.getChildCount();
        for(int i = 0; i < count; i++){
            View child = numberPicker.getChildAt(i);
            if(child instanceof EditText){
                try{
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint)selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((EditText)child).setTextColor(color);
                    numberPicker.invalidate();
                    return true;
                }
                catch(NoSuchFieldException e){
                    Log.w("setNumberPicker", e);
                }
                catch(IllegalAccessException e){
                    Log.w("setNumberPicker", e);
                }
                catch(IllegalArgumentException e){
                    Log.w("setNumberPicker", e);
                }
            }
        }
        return false;
    }
}
