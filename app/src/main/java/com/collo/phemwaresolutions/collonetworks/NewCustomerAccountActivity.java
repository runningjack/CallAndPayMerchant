package com.collo.phemwaresolutions.collonetworks;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class NewCustomerAccountActivity extends AppCompatActivity {

    public static final String HOST_ADDRESS = "http://192.168.8.100/CallAndPayAPI/api";


    private DatePickerDialog toDatePickerDialog;
    SimpleDateFormat dateFormatter;
    private EditText ageTxt;
    private DatePickerDialog ageDatePickerDialog;

    /* This is for web API */
    private BackGroundTask bgt;
    ArrayList<State> stateList = new ArrayList<State>();

    private String TAG_DATA = "data";
    private Spinner stateField;
    private String TAG_ID_STATE;
    private String TAG_NAME;
    private String TAG_CURRENCY;
    GlobalClassMethods gbc = new GlobalClassMethods();
    private TextView fnTxt;
    private Spinner titleSpin;
    private TextView lnTxt;
    private TextView mnTxt;
    private TextView compTxt;
    private TextView addTxt;
    private TextView cityTxt;
    private Spinner stateSpin;
    private TextView phoneTxt;
    private TextView emailTxt;
    private Spinner genderSpin;
    private TextView occupTxt;
    private TextView pinTxt;
    private TextView pwdTxt;
    private String respId;
    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer_account);

        getSupportActionBar().setTitle("Customer Registration");
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        setUpControls();

    }


    void setUpControls() {
        titleSpin = (Spinner) findViewById(R.id.spinnerTitle);
        fnTxt = (TextView) findViewById(R.id.txtFirstName);
        lnTxt = (TextView) findViewById(R.id.txtLastName);
        mnTxt = (TextView) findViewById(R.id.txtMiddleName);
        compTxt = (TextView) findViewById(R.id.txtCompany);
        addTxt = (TextView) findViewById(R.id.txtAddress);
        cityTxt = (TextView) findViewById(R.id.txtCity);
        stateSpin = (Spinner) findViewById(R.id.spinnerState);
        phoneTxt = (TextView) findViewById(R.id.txtPhone);
        emailTxt = (TextView) findViewById(R.id.txtEmail);
        genderSpin = (Spinner) findViewById(R.id.spinnerGender);
        occupTxt = (TextView) findViewById(R.id.txtOccupation);
        pinTxt = (TextView) findViewById(R.id.txtCustomerPin);
        pwdTxt = (TextView) findViewById(R.id.txtCustomerPassword);

        //states
        //buildStatesDropDown();

    }

    private void buildStatesDropDown()
    {
        bgt = new BackGroundTask(HOST_ADDRESS+"/states", "GET");
        try
        {
            JSONArray statesJSON = bgt.execute().get();
            if (statesJSON != null)
            {
                // looping through All states
                for (int i = 0; i < statesJSON.length(); i++) {
                    JSONObject c = statesJSON.getJSONObject(i);
                    // Storing each json item in variable
                    String id = c.getString("Id");
                    String name = c.getString("States");

                    // add state
                    stateList.add(new State(id, name.toUpperCase()));
                }

                // bind adapter to spinner
                stateField = (Spinner) findViewById(R.id.spinnerState);
                CountryAdapter cAdapter = new CountryAdapter(this, android.R.layout.simple_spinner_item, stateList);
                stateField.setAdapter(cAdapter);

                stateField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        State selectedState = stateList.get(position);
                        Toast.makeText(NewCustomerAccountActivity.this, selectedState.getState() + " was selected!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }

                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void getListOfStates() {
        ArrayList<State> states = new ArrayList<State>();
        ArrayList<String> stateNames = new ArrayList<String>();

        try {
            JSONObject json = new JSONObject("");
            JSONArray jsonArray = new JSONArray(json.optString("states"));
            Log.i(NewCustomerAccountActivity.class.getName(), "Number of entries " + jsonArray.length());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                State dState = new State();
                dState.setState(jsonObject.optString("State"));
                dState.setId(jsonObject.optString("Id"));
                states.add(dState);
                stateNames.add(jsonObject.optString("State"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Spinner stateSpinner = (Spinner) findViewById(R.id.spinnerState);
        stateSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, stateNames));
    }


    public void gotoTakePicture(View view)
    {
        String p = constructAndPostCustomer();
        if (p == "1") {
            Intent i = new Intent(this, TakePictureActivity.class);
            startActivity(i);
        } else {
            Toast.makeText(NewCustomerAccountActivity.this, "POST DATA FAIL", Toast.LENGTH_LONG);
        }

    }

    String constructAndPostCustomer() {
        //check network before the construct

        //do the construct
        //bgt = new BackGroundTask(MAP_API_URL, "GET", apiParams);
        //you need to encode ONLY the values of the parameters
        String param="";
        try {
            param="param1=" + URLEncoder.encode("value1","UTF-8") + "&param2="+URLEncoder.encode("value2","UTF-8")+"&param3="+ URLEncoder.encode("value3","UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        bgt = new BackGroundTask(HOST_ADDRESS + "/Customers", "GET");
        try {
            JSONArray result = bgt.execute().get();
            if (result != null)
            {
                JSONObject object = new JSONObject(result.get(0).toString());// if you have only one element then get 0th index
                JSONArray a = object.getJSONArray("data");
                for (int i = 0; i < a.length(); ++i) {
                    Log.d("POST ERROR", a.getString(0));
                }
                return respId;
            } else
            {
                Log.d("POST ERROR", "POST");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "0";
    }


}
