package com.technozion.tz_17;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity{
    private static Login mInstance;
    private SharedPreferences sharedPreferences;
    private static String token,username,email,tzid;
    private Dialog loading_dialog = null;
    public static synchronized Login getInstance(){
        return mInstance;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.event_app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawer drawer= (Drawer) getSupportFragmentManager().findFragmentById(R.id.drawer_fragment);
        drawer.setup(R.id.drawer_fragment, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);


        mInstance = this;
        sharedPreferences = getSharedPreferences(Constants.APP_PREFERENCE, Context.MODE_PRIVATE);
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((TextView) findViewById(R.id.input_username)).getText().toString().trim();
                String password = ((TextView) findViewById(R.id.input_password)).getText().toString().trim();
                if(isNetworkAvailable()) {
                    NetworkRequests.Login(email, password);
                    showProgress(true);
                }else{
                    Toast.makeText(getApplicationContext(), "Check Network Connectivity", Toast.LENGTH_SHORT).show();
                }
            }
        });
        TextView sign_up = (TextView) findViewById(R.id.link_signup);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });
    }


    public void Loggedin(JSONObject response) {
        System.out.println(response);
        try {
            token = response.getString("token");
            username = response.getString("firstname");
            email = response.getString("email");
            tzid = response.getString("tzid");
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Constants.TOKEN = token;
            editor.putString(Constants.TOKEN_STR, Constants.TOKEN);
            editor.putString(Constants.isSignedIN, "1");
            editor.putString(Constants.username, username);
            editor.putString(Constants.email, email);
            editor.putString(Constants.tzid, tzid);
            editor.commit();
            NetworkRequests.ReglogCheck();
            showProgress(true);

//            NetworkRequests.ReglogCheck();
            Toast.makeText(this, "User Logged in ", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        } catch (JSONException e) {
            e.printStackTrace();
            showProgress(false);
        }
    }

    public void Registered(JSONObject response) {
        try {
            if (response.getString("regfee").equals("True")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constants.regfee, response.getString("regfee"));
                editor.commit();
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constants.regfee, response.getString("regfee"));
                editor.commit();
            }

            if (response.getString("hospfee").equals("True")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constants.hospfee, response.getString("hospfee"));
                editor.commit();
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constants.hospfee, response.getString("regfee"));
                editor.commit();
            }

            if (response.getString("regfee").equals("True") || response.getString("hospfee").equals("True")){
                int a = 3;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        showProgress(false);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void showProgress(final boolean show) {
        try {
            if (show) {
                if (loading_dialog != null) {
                    loading_dialog.dismiss();
                    loading_dialog = null;
                }
                loading_dialog = new Dialog(getInstance());
                loading_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                loading_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                loading_dialog.setCancelable(false);
                loading_dialog.setContentView(R.layout.loading_dialog_layout);
                loading_dialog.show();
            } else {
                if (loading_dialog != null) {
                    loading_dialog.dismiss();
                    loading_dialog = null;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void NotPaid(JSONObject response) {
        showProgress(false);
    }

    public void Invaliddata(JSONObject response) {
        showProgress(false);
        Toast.makeText(getApplicationContext(), "Invalid email or password ", Toast.LENGTH_SHORT).show();
    }

    public void UpdateProfile(JSONObject response) throws JSONException {
        token = response.getString("token");
        username = response.getString("firstname");
        email = response.getString("email");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Constants.TOKEN = token;
        editor.putString(Constants.TOKEN_STR, Constants.TOKEN);
        editor.putString(Constants.isSignedIN, "1");
        editor.putString(Constants.username, username);
        editor.putString(Constants.email, email);
        editor.commit();
        showProgress(false);
        Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, UpdateProfile.class);
        startActivity(i);
        finish();
    }

    public void onRequestFailure(int i, String s) {
        showProgress(false);
        Toast.makeText(getApplicationContext(), "Check your Network", Toast.LENGTH_SHORT).show();
    }
}


//
//public class Login extends ActionBarActivity {
//    static int login = 0;
//    private ProgressDialog mProgressDialog;
//
//    private void showProgressDialog() {
//        if (mProgressDialog == null) {
//            mProgressDialog = new ProgressDialog(this);
//            mProgressDialog.setMessage("Loggin you in.");
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
//    class LoginAsync extends AsyncTask<String, Void, String> {
//        Context context;
//        String username = "", password = "";
//
//        public LoginAsync(Context context) {
//            this.context = context;
//        }
//
//        @Override
//        protected String doInBackground(String... arg) {
//            try {
//                username = (String) arg[0];
//                password = (String) arg[1];
//
//                String link = "http://register.springspree.in/accounts/login_mobile";
//                String data = URLEncoder.encode("inputEmail", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
//                data += "&" + URLEncoder.encode("inputPassword", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
//
//                URL url = new URL(link);
//                URLConnection conn = url.openConnection();
//
//                conn.setDoOutput(true);
//                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//
//                wr.write(data);
//                wr.flush();
//
//                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//
//                StringBuilder sb = new StringBuilder();
//                String line = null;
//
//                // Read Server Response
//                while ((line = reader.readLine()) != null) {
//                    sb.append(line);
//                    break;
//                }
//                return sb.toString();
//            } catch (Exception e) {
//
//                return new String("Exception: " + e.getMessage());
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            boolean flag = true;
//            if (s.equals("0"))
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
//                Toast.makeText(getApplicationContext(),"Successfully logged in.\n User id : "+s,Toast.LENGTH_SHORT).show();;
//                SharedPreferences.Editor editor = getSharedPreferences("spree_login", MODE_PRIVATE).edit();
//                editor.putString("username", username);
//                editor.putString("userid", s);
//                editor.commit();
//
//
//                Intent I = new Intent(getApplicationContext(), MainActivity.class);
//                I.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(I);
//
//            } else {
//
//                hideProgressDialog();
//                TextView error = (TextView) findViewById(R.id.error);
//                error.setText("Error logging in. Please check your username/password");
//                new LoginAsync(getApplicationContext()).execute(username, password);
//            }
//        }
//    }
//
//
//    EditText username, password;
//    TextView sign_up;
//    android.support.v7.widget.AppCompatButton login_button;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        SharedPreferences prefs = getSharedPreferences("spree_login", MODE_PRIVATE);
//        String restoredText = prefs.getString("userid", null);
//        if (restoredText != null) {
//            Intent I = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(I);
//        }
//        setContentView(R.layout.activity_login);
//        username = (EditText) findViewById(R.id.input_username);
//        password = (EditText) findViewById(R.id.input_password);
//        sign_up = (TextView) findViewById(R.id.link_signup);
//        sign_up.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Login.this, Register.class));
//            }
//        });
//
//        login_button = (AppCompatButton) findViewById(R.id.btn_login);
//        login_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (isNetworkAvailable()) {
//                    showProgressDialog();
//                    new LoginAsync(getApplicationContext()).execute(username.getText().toString(), password.getText().toString());
//                } else
//                    Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_login, menu);
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
//      /*  if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);*/
//        return false;
//    }
//}
