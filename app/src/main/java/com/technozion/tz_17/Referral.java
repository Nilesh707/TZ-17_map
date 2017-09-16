package com.technozion.tz_17;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Anshul Goyal on 08-10-2016.
 */

public class Referral extends ActionBarActivity{

    String money="0",credits="0";
    String tz_id;
    private Dialog loading_dialog = null;
    TextView money_tv,credits_tv;

    private static Referral mInstance;
    private SharedPreferences sharedPreferences;
    public static synchronized Referral getInstance(){
        return mInstance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referral);


        Toolbar toolbar;

        toolbar = (Toolbar) findViewById(R.id.event_app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawer drawer= (Drawer) getSupportFragmentManager().findFragmentById(R.id.drawer_fragment);
        drawer.setup(R.id.drawer_fragment, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        mInstance = this;
        sharedPreferences = getSharedPreferences(Constants.APP_PREFERENCE, MODE_PRIVATE);

        money_tv = (TextView) findViewById(R.id.refer_money);
        credits_tv = (TextView) findViewById(R.id.refer_credits);


        tz_id = Constants.gettz_id(this);
        ImageView share_btn;
        share_btn = (ImageView) findViewById(R.id.share_button);


        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    try {
                        if (Constants.LoginState(getApplicationContext()).equals("1")) {
                            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                            sharingIntent.setType("text/plain");
                            String shareBody = "Technozion is one of the largest technical festivals in south india " +
                                    " and is being organized by NIT Warangal, 21-23 October 2016. \n It is an experience" +
                                    " you should not miss! Register using the referral link I am passing, and get 50" +
                                    " points! Refer your friends to get back Rs. 500 for every 500 points you make!" +
                                    " \n Register for Technozion using the referral link: " +
                                    "http://www.technozion.org/register/login/?referralCode=" + tz_id;
                            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                            startActivity(Intent.createChooser(sharingIntent, "Share via"));

                        } else {
                            Toast.makeText(getApplicationContext(), "Please login/register and then share and earn. ", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch(Exception e)
                    {
                        Toast.makeText(getApplicationContext(), "Please first login/register to share and earn. ", Toast.LENGTH_SHORT).show();
                    }
            }
        });
        NetworkRequests.getReferal();
        showProgress(true);
    }
    public void onSuccessRef(JSONObject response) throws JSONException {
        money = response.getString("money");
        credits = response.getString("credits");
        money_tv.setText("Total Money Earned : " + money);
        credits_tv.setText("Total Credits Earned : " + credits);
        showProgress(false);
    }

    public void onRequestFailure(int i, String s) {
        Toast.makeText(getApplicationContext(), "Network Error. Please try again", Toast.LENGTH_SHORT).show();
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

    public void onLogerrorRef(JSONObject response) {

        showProgress(false);
        Toast.makeText(getApplicationContext(), "Please Login/Register to continue", Toast.LENGTH_SHORT).show();

    }
}
