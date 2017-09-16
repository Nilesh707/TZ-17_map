package com.technozion.tz_17;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Kiran Konduru on 10/4/2016.
 */

public class Profile extends AppCompatActivity{

    private static Profile mInstance;
    SharedPreferences sharedPreferences;
    private Dialog loading_dialog = null;
    static Bitmap bitmap;

    RelativeLayout regNotPaidView, regPaidView;
    RelativeLayout hospitalityNotPaidView, hospitlalityPaidView;
    CardView payBothView;
    TextView nameView, emailView;

    public static synchronized Profile getInstance(){
        return mInstance;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.event_app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawer drawer= (Drawer) getSupportFragmentManager().findFragmentById(R.id.drawer_fragment);
        drawer.setup(R.id.drawer_fragment, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        mInstance = this;
        if(isNetworkAvailable()) {
            NetworkRequests.CheckAppUser();
            showProgress(true);
//            NetworkRequests.getAllEvents();
//            NetworkRequests.getReferal();
        }else{
            Toast.makeText(getApplicationContext(), "Check Network Connection", Toast.LENGTH_SHORT).show();
        }
        sharedPreferences = getSharedPreferences(Constants.APP_PREFERENCE, MODE_PRIVATE);

        hospitlalityPaidView = (RelativeLayout) findViewById(R.id.hospitalityPaidView);
        hospitalityNotPaidView = (RelativeLayout) findViewById(R.id.hospitalityNotPaidView);
        regNotPaidView = (RelativeLayout) findViewById(R.id.registrationNotPaidView);
        regPaidView = (RelativeLayout) findViewById(R.id.registrationPaidView);
        payBothView = (CardView) findViewById(R.id.payBothContainer);
        nameView = (TextView) findViewById(R.id.nameView);
        emailView = (TextView) findViewById(R.id.emailView);



        findViewById(R.id.regfeebutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkRequests.addAppReg();
                showProgress(true);
//                Intent i = new Intent(getApplicationContext(), WebPayment.class);
//                startActivity(i);
            }
        });
        findViewById(R.id.hospfeebutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkRequests.addAppHosp();
                showProgress(true);
            }
        });
        findViewById(R.id.payboth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkRequests.addAppBoth();
                showProgress(true);
            }
        });

        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I=null;
                sharedPreferences.edit().clear().commit();
                I=new Intent(getApplicationContext(),Login.class);
                I.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(I);
                finish();
            }

        });

        findViewById(R.id.qrimage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Popup_qr.class);
                startActivity(i);
            }
        });

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


    public void Registered(JSONObject response) {
        String tzid = null;
        try {
            tzid = response.getString("tzid");
            TextView t1 = (TextView) findViewById(R.id.tzidprof);
            t1.setText(tzid);
            generateQR(tzid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if (response.getString("regfee").equals("true")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constants.regfee, response.getString("regfee"));
                editor.commit();
//                Button regbut = (Button) findViewById(R.id.regfeebutton);
                regNotPaidView.setVisibility(View.GONE);
                regPaidView.setVisibility(View.VISIBLE);
//                regbut.setVisibility(View.GONE);
            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constants.regfee, response.getString("regfee"));
                editor.commit();
                regNotPaidView.setVisibility(View.VISIBLE);
                regPaidView.setVisibility(View.GONE);
            }

            if (response.getString("hospfee").equals("true")){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constants.hospfee, response.getString("hospfee"));
                editor.commit();
//                Button hospbut = (Button) findViewById(R.id.hospfeebutton);
//                hospbut.setVisibility(View.GONE);
                hospitlalityPaidView.setVisibility(View.VISIBLE);
                hospitalityNotPaidView.setVisibility(View.GONE);

            }else{
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constants.hospfee, response.getString("regfee"));
                editor.commit();
                hospitlalityPaidView.setVisibility(View.GONE);
                hospitalityNotPaidView.setVisibility(View.VISIBLE);
            }

//            TextView tv2 = (TextView) findViewById(R.id.payboth);
            if (response.getString("regfee").equals("true") || response.getString("hospfee").equals("true")) {
                payBothView.setVisibility(View.GONE);
            }

            setNameEmail();

            showProgress(false);

        } catch (JSONException e) {
            e.printStackTrace();
            showProgress(false);
        }
    }
//
    public void generateQR(String tzid){
        String name = Constants.getUsername(this);
        String text2Qr = tzid;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(bitMatrix);
            ImageView imageview = (ImageView) findViewById(R.id.qrimage);
            imageview.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public void onEventSuccess(JSONObject response){
        System.out.println(response);
        try {
            JSONObject events = (JSONObject) response.get("events");
            int length = events.length();
            for(int i=0;i<length;i++){
                String s = events.getString(String.valueOf(i));
                System.out.println(s);
                final TextView rowTextView = new TextView(this);
                rowTextView.setText(s);
                rowTextView.setGravity(Gravity.CENTER);
                int k = i+1;
                rowTextView.setId(k);
//                    rowTextView.setTextSize(Float.parseFloat("25"));
                rowTextView.setTextColor(Color.BLUE);
                rowTextView.setBackgroundColor(Color.WHITE);
//                    rowTextView.setBackgroundResource(R.drawable.card3);
                rowTextView.setGravity(Gravity.CENTER);
//                    rowTextView.setLayoutParams(new TableRow.LayoutParams(0 , LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                rowTextView.setPadding(1,1,1,1);
                LinearLayout l = (LinearLayout) findViewById(R.id.allevents);
                l.addView(rowTextView);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) rowTextView.getLayoutParams();
                rowTextView.setId(i);
                rowTextView.setTextSize(Float.parseFloat("20"));
                rowTextView.setTextColor(Color.parseColor("#FFFFFF"));
                rowTextView.setBackgroundColor(Color.parseColor("#137977"));
                rowTextView.setPadding(10,10,10,10);
                rowTextView.setShadowLayer(5,5,5, Color.parseColor("#1A237E"));
                params.setMargins(10, 10, 10, 10);
                rowTextView.setLayoutParams(params);
                showProgress(false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            showProgress(false);
        }
    }
    public void onSuccessRef(JSONObject response) {
        System.out.println(response);
        showProgress(false);
    }

    public void NotPaid(JSONObject response) {
//        TextView tv = (TextView) findViewById(R.id.regfee);
//        tv.setText("False");
//        TextView tv1 = (TextView) findViewById(R.id.hospfee);
//        tv1.setText("False");
        showProgress(false);
    }

    public void onRequestFailure(int i, String s) {
        Toast.makeText(getApplicationContext(), "Some error occured. Please try again", Toast.LENGTH_SHORT).show();
        showProgress(false);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void onRegistered(JSONObject response){
        NetworkRequests.RegCheck();
        showProgress(true);
    }

    public void onNotRegistered(JSONObject response){
        Toast.makeText(getApplicationContext(), "Complete Profile details ", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, UpdateProfile.class);
        startActivity(i);
        finish();
        showProgress(false);
    }

    public void onRegRespSuccess(JSONObject response) {

        try {
            String trans_id = response.getString("transaction_id");
            String trans_amount = response.getString("transaction_amount");
            String hash = response.getString("hash");
            String salt = response.getString("salt");
            Intent i = new Intent(this, WebPayment.class);
            i.putExtra("Amount", trans_amount);
            i.putExtra("trans_id", trans_id);
            i.putExtra("hash",hash);
            i.putExtra("salt",salt);
            startActivity(i);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        showProgress(false);
    }

    void setNameEmail() {
        SharedPreferences sp = getSharedPreferences(Constants.APP_PREFERENCE, Context.MODE_PRIVATE);
        String loggedIn = sp.getString(Constants.isSignedIN, null);
        if (loggedIn != null && loggedIn.equals("1")) {
            String name = sp.getString(Constants.username, null);
            String email = sp.getString(Constants.email, null);
            if (name != null)
                nameView.setText(name);
            if (email != null)
                emailView.setText(email);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
