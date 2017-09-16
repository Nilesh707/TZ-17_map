package com.technozion.tz_17;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {

    private static ApiEndpointInterface apiService;
    TextView login;
    android.support.v7.widget.AppCompatButton register_button;
    EditText firstname, lastname, email, password, confpass, phone, college, sex, city, state, collegeId, referal;
    private static Register mInstance;
    private Dialog loading_dialog = null;
    private SharedPreferences sharedPreferences;

    public static synchronized Register getInstance() {
        return mInstance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.event_app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawer drawer= (Drawer) getSupportFragmentManager().findFragmentById(R.id.drawer_fragment);
        drawer.setup(R.id.drawer_fragment, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);


        mInstance = this;
        sharedPreferences = getSharedPreferences(Constants.APP_PREFERENCE, MODE_PRIVATE);
        SharedPreferences prefs = getSharedPreferences("spree_login", MODE_PRIVATE);
        String restoredText = prefs.getString("userid", null);
        if (restoredText != null) {
            Intent I = new Intent(getApplicationContext(), TZ_main.class);
            startActivity(I);
        }
        login = (TextView) findViewById(R.id.link_login);
        firstname = (EditText) findViewById(R.id.input_firstname);
        lastname = (EditText) findViewById(R.id.input_lastname);
        email = (EditText) findViewById(R.id.input_email);
        password = (EditText) findViewById(R.id.input_register_password);
        confpass = (EditText) findViewById(R.id.input_register_conf_password);
//        password1 ?=
//        phone = (EditText) findViewById(R.id.input_phone);
//        college = (EditText) findViewById(R.id.input_college);
//        collegeId = (EditText) findViewById(R.id.input_college_id);
//        sex = (EditText) findViewById(R.id.input_sex);
//        city = (EditText) findViewById(R.id.input_city);
//        state = (EditText) findViewById(R.id.input_state);
//        referal = (EditText) findViewById(R.id.input_ref);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });

        register_button = (AppCompatButton) findViewById(R.id.btn_signup);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    if(ifSamepass(password.getText().toString(), confpass.getText().toString())){
                        if(password.getText().toString().length()>=8) {
                            NetworkRequests.Register(firstname.getText().toString(), lastname.getText().toString(), email.getText().toString(), password.getText().toString());
                            showProgress(true);
                        }else{
                            Toast.makeText(getApplicationContext(), "Password must be atleast 8 characters long" , Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean ifSamepass(String s, String s1) {

        if(!s.equals(s1)){
            return false;
        }else return true;

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }


    public void Registered(JSONObject response) {
        System.out.println(response);
        try {
            Intent i = new Intent(this, MainActivity.class);
            Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show();
            startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
        showProgress(false);
    }


    public void alreadyRegistered(JSONObject response) {

        try {
            String message = response.getString("message");
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        showProgress(false);
    }

    public void onRequestFailure(int i, String s) {
        Toast.makeText(this, "Check Network Connection", Toast.LENGTH_SHORT).show();
        showProgress(false);
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

//    private static ApiEndpointInterface apiService;
//    TextView login;
//    android.support.v7.widget.AppCompatButton register_button;
//    EditText username, email, password, phone, college, sex, city, state, collegeId;
//    private ProgressDialog mProgressDialog;
//
//    private void showProgressDialog() {
//        if (mProgressDialog == null) {
//            mProgressDialog = new ProgressDialog(this);
//            mProgressDialog.setMessage("Signing you up.");
//            mProgressDialog.setIndeterminate(true);
//            mProgressDialog.setCancelable(false);
//        }
//        mProgressDialog.show();
//    }
//
//    private void hideProgressDialog() {
//        if (mProgressDialog != null && mProgressDialog.isShowing())
//            mProgressDialog.hide();
//    }
//
//    class SignupAsync extends AsyncTask<String, String, String> {
//
//        String inputName;
//        String inputEmail;
//        String inputPhone;
//        String inputCollege;
//        String inputGender;
//        String inputCollegeId;
//        String inputCity;
//        String inputState;
//        String inputPassword;
//        Context context;
//
//        SignupAsync(Context context, String Name, String Email, String Phone, String College, String Gender, String CollegeId, String City, String State, String Password) {
//            this.context = context;
//            this.inputName = Name;
//            this.inputEmail = Email;
//            this.inputPhone = Phone;
//            this.inputCollege = College;
//            this.inputGender = Gender;
//            this.inputCollegeId = CollegeId;
//            this.inputCity = City;
//            this.inputState = State;
//            this.inputPassword = Password;
//
//
//        }
//
//        @Override
//        protected String doInBackground7(String... arg) {
//            ArrayList<String> arr = new ArrayList<>();
//            arr.add("123");
//            arr.add("345");
//            Call<String> call = Register.apiService.signUpMobile(new SignupRetrofitClass(arr, this.inputName, this.inputEmail, this.inputPhone, this.inputCollege, this.inputGender, this.inputCollegeId,
//                    this.inputCity, this.inputState, this.inputPassword));
//            String response = "";
//            try {
//                response = call.execute().body();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            Log.d("retrofitsignup", response);
//            return response;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            Log.d("retrofitsignup", s);
//            boolean flag = true;
//            if (s.equals("-1"))
//                flag = false;
//            else {
//                for (int i = 0; i < s.length(); i++) {
//                    if (s.charAt(i) < 48 || s.charAt(i) > 57) {
//                        flag = false;
//                        break;
//                    }
//                }
//            }
//            if (flag) {
//                hideProgressDialog();
//                SharedPreferences.Editor editor = getSharedPreferences("spree_login", MODE_PRIVATE).edit();
//                editor.putString("username", this.inputName);
//                editor.putString("userid", s);
//                editor.commit();
//                Toast.makeText(getApplicationContext(),"Successfully logged in.\n User id : "+s,Toast.LENGTH_SHORT).show();;
//                Intent I = new Intent(getApplicationContext(), MainActivity.class);
//                I.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(I);
//
//            } else {
//                hideProgressDialog();
//                TextView error = (TextView) findViewById(R.id.reg_error);
//                error.setText("Error signing up. Email ID already exists.");
//            }
//        }
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//        SharedPreferences prefs = getSharedPreferences("spree_login", MODE_PRIVATE);
//        String restoredText = prefs.getString("userid", null);
//        if (restoredText != null) {
//            Intent I = new Intent(getApplicationContext(), TZ_main.class);
//            startActivity(I);
//        }
//        login = (TextView) findViewById(R.id.link_login);
//        username = (EditText) findViewById(R.id.input_name);
//        email = (EditText) findViewById(R.id.input_email);
//        password = (EditText) findViewById(R.id.input_register_password);
//        phone = (EditText) findViewById(R.id.input_phone);
//        college = (EditText) findViewById(R.id.input_college);
//        collegeId = (EditText) findViewById(R.id.input_college_id);
//        sex = (EditText) findViewById(R.id.input_sex);
//        city = (EditText) findViewById(R.id.input_city);
//        state = (EditText) findViewById(R.id.input_state);
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Constants.base_url_reg)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        Register.apiService = retrofit.create(ApiEndpointInterface.class);
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Register.this, Login.class));
//            }
//        });
//        register_button = (AppCompatButton) findViewById(R.id.btn_signup);
//        register_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (isNetworkAvailable()) {
//                    showProgressDialog();
//                    new SignupAsync(getApplicationContext(), username.getText().toString(), email.getText().toString(), phone.getText().toString(),
//                            college.getText().toString(), sex.getText().toString(), collegeId.getText().toString(), city.getText().toString(), state.getText().toString(),
//                            password.getText().toString()).execute();
//                }
//                else
//                    Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_register, menu);
//        return true;
//    }
//
//    private boolean isNetworkAvailable() {
//        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//       /* if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);*/
//        return false;
//    }
