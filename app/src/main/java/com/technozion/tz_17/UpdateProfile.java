package com.technozion.tz_17;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Kiran Konduru on 10/11/2016.
 */

public class UpdateProfile extends AppCompatActivity{

    private static UpdateProfile mInstance;
    private Dialog loading_dialog = null;
    private String state = null, city = null ,college = null, rcode,phone,gender,collegeid;
    private ArrayList<String> college_list, college_index;
    int flag=0;
    private int citypos,clgpos;
    protected static synchronized UpdateProfile getInstance(){
        return mInstance;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        mInstance = this;
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.event_app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawer drawer= (Drawer) getSupportFragmentManager().findFragmentById(R.id.drawer_fragment);
        drawer.setup(R.id.drawer_fragment, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);


        ((Spinner) findViewById(R.id.college_spinner)).setVisibility(View.GONE);
        ((Spinner) findViewById(R.id.state_spinner)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<String> state_code = Arrays.asList(getResources().getStringArray(R.array.state_name_items));
                if (position == 0)
                    state = null;
                else {
                    state = state_code.get(position);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                state = null;
            }
        });

        ((Spinner) findViewById(R.id.city_spinner)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<String> city_code = Arrays.asList(getResources().getStringArray(R.array.city_list));
                if (position == 0)
                    city = null;
                else {
                    city = city_code.get(position);
                    if(city.equals("other")){
                        findViewById(R.id.othercity).setVisibility(View.VISIBLE);
                        findViewById(R.id.otherclg).setVisibility(View.VISIBLE);
                        clgpos = 4000;
                        citypos = 700;
                        findViewById(R.id.college_spinner).setVisibility(View.GONE);
                        flag=1;
                    }else {
                        findViewById(R.id.othercity).setVisibility(View.GONE);
                        findViewById(R.id.otherclg).setVisibility(View.GONE);
                        flag=0;
                        citypos = position;
                        NetworkRequests.getCollegeList(position);
                        showProgress(true);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                city = null;
            }
        });

        ((Spinner) findViewById(R.id.input_sex)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<String> gender_code = Arrays.asList(getResources().getStringArray(R.array.gender_spinner_items));
                    gender = gender_code.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                city = null;
            }
        });


        EditText ephone = (EditText) findViewById(R.id.input_phone);
        EditText ercode = (EditText) findViewById(R.id.input_ref);
        EditText ecid = (EditText) findViewById(R.id.input_college_id);

        rcode = ercode.getText().toString();
        phone = ephone.getText().toString();
        collegeid = ecid.getText().toString();

        findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkdata()) {
                    if(flag==1){
                        EditText e1 = (EditText) findViewById(R.id.otherclg);
                        EditText e2 = (EditText) findViewById(R.id.othercity);
                        String c1 = e1.getText().toString();
                        String c2 = e2.getText().toString();

                        if(c1.equals("")){
                            e1.setError("This field is required");
                        }
                        if(c2.equals("")){
                            e2.setError("This field is required");
                        }else NetworkRequests.UpdateProfile(rcode, phone, clgpos, citypos, c1, c2, state, gender, collegeid);
                    }else {
                        NetworkRequests.UpdateProfile(rcode, phone, clgpos, citypos, "", "", state, gender, collegeid);
                    }
                    showProgress(true);
                }
            }
        });
    }

    public void Updated(JSONObject response) {
        System.out.println(response);
        Intent i = new Intent(this, Login.class);
        Toast.makeText(getApplicationContext(), "Login to continue", Toast.LENGTH_SHORT).show();
        startActivity(i);
        showProgress(false);
    }

    public void onRequestFailure(int i, String s) {
        Toast.makeText(this, "Check Network Connection", Toast.LENGTH_SHORT).show();
        showProgress(false);
    }

    public Boolean checkdata(){
        EditText ephone = (EditText) findViewById(R.id.input_phone);
        EditText ercode = (EditText) findViewById(R.id.input_ref);
        EditText ecid = (EditText) findViewById(R.id.input_college_id);

        rcode = ercode.getText().toString();
        phone = ephone.getText().toString();
        collegeid = ecid.getText().toString();

        if(phone.length()!=10){
            ephone.setError("Phone Number must be 10 digits long");
            Toast.makeText(getApplicationContext(), "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(collegeid.length()==0){
            ecid.setError("This Field is required");
            Toast.makeText(getApplicationContext(), "Id is required ", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;

    }

    public void onCollegeListSuccess(JSONObject response) throws JSONException {
        showProgress(false);
        System.out.println(response);
        ((Spinner) findViewById(R.id.college_spinner)).setVisibility(View.VISIBLE);
        college_list = new ArrayList<String>();
        college_index = new ArrayList<String>();
        try {
            JSONArray cityArray = response.getJSONArray("results");
            for (int i = 0; i < cityArray.length(); i++) {
                JSONObject actor = cityArray.getJSONObject(i);
                String name = actor.getString("text");
                String index = actor.getString("value");
                college_list.add(name);
                college_index.add(index);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, college_list);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Spinner sItems = (Spinner) mInstance.findViewById(R.id.college_spinner);
            sItems.setAdapter(adapter);
            ((Spinner) findViewById(R.id.college_spinner)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    clgpos = Integer.parseInt(college_index.get(position));
                    college = college_list.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    college = null;
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void showProgress(final boolean show) {
        if (show) {
            if (loading_dialog != null)
                loading_dialog.dismiss();
            loading_dialog = new Dialog(getInstance());
            loading_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            loading_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            loading_dialog.setCancelable(false);
            loading_dialog.setContentView(R.layout.loading_dialog_layout);
            loading_dialog.show();
        } else {
            if (loading_dialog != null){
                loading_dialog.dismiss();
                loading_dialog = null;
            }
        }
    }

}
