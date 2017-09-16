package com.technozion.tz_17;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.payu.india.Model.PayuHashes;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Iterator;


public class About extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar;

        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawer drawer= (Drawer) getSupportFragmentManager().findFragmentById(R.id.drawer_fragment);
        drawer.setup(R.id.drawer_fragment, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

//        PaymentParams mPaymentParams = new PaymentParams();
//        mPaymentParams.setKey("I2XJqe");
//        mPaymentParams.setAmount("15.0");
//        mPaymentParams.setProductInfo("Registration");
//        mPaymentParams.setFirstName("Testing name");
//        mPaymentParams.setEmail("kiranckonduru@gmail.com");
//        mPaymentParams.setTxnId("0123479543689");
//        mPaymentParams.setSurl("httpss://payu.herokuapp.com/success");
//        mPaymentParams.setFurl("httpss://payu.herokuapp.com/failure");
//        Intent intent = new Intent(getApplicationContext(), PayUBaseActivity.class);
//        PayuHashes payuHashes = new PayuHashes();
//        mPaymentParams.setHash(String.valueOf(payuHashes));
//        intent.putExtra(PayuConstants.ENV, PayuConstants.PRODUCTION_ENV);
//        intent.putExtra(PayuConstants.PAYMENT_DEFAULT_PARAMS, mPaymentParams);
//        intent.putExtra(PayuConstants.PAYU_HASHES, payuHashes);
//        startActivity(intent);

        //TODO: To be updated
        setTitle("Theme");
        TextView text = (TextView) findViewById(R.id.theme_text);
        text.setText("Presenting \"Chronos\" as the theme for the eleventh edition of Technozion, the blend of technology and mythology. A 3-day peek into technology accompanied by a mythological eye.\n" +
                "\n" +
                "There once was a wise old man: Husband to the Mother Earth and a proud parent of time. He terrified people, for he devoured everyone and everything. With a scythe and an hour glass, the former being less fatal than the latter, he treaded along the only direction he knew. He was omnipresent, butchering lives and destroying souls. The dimensions to him were frivolous, so he created the fourth one. A toy for many, the hour glass to him was a tool, to control and manipulate his only child. He thus became the most powerful and feared entity there ever was.\n" +
                "\n" +
                "The old man had another face to his existence, the one no one knew. For everything he devoured, there was something better and evolved. For every life he took, there were many that flourished. His wrinkled skin personified the end; an end to everything including him.\n" +
                "\n" +
                "As a witnesse of every disruptive technological revolution, like how mean e-mail was to telegrams, the old man will be here, at NIT Warangal, introducing you to his child. He’ll guide you and take you from past to present and from present to future, taking pride in every disruption he ever caused and witnessed. Be nice to him, and he may even let you peek into the future.");
    }

    protected PayuHashes doInBackground(String ... postParams) {
        PayuHashes payuHashes = new PayuHashes();
        try {
//                URL url = new URL(PayuConstants.MOBILE_TEST_FETCH_DATA_URL);
//                        URL url = new URL("http://10.100.81.49:80/merchant/postservice?form=2");;

            URL url = new URL("https://payu.herokuapp.com/get_hash");

            // get the payuConfig first
            String postParam = postParams[0];

            byte[] postParamsByte = postParam.getBytes("UTF-8");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(postParamsByte.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postParamsByte);

            InputStream responseInputStream = conn.getInputStream();
            StringBuffer responseStringBuffer = new StringBuffer();
            byte[] byteContainer = new byte[1024];
            for (int i; (i = responseInputStream.read(byteContainer)) != -1; ) {
                responseStringBuffer.append(new String(byteContainer, 0, i));
            }

            JSONObject response = new JSONObject(responseStringBuffer.toString());

            Iterator<String> payuHashIterator = response.keys();
            while(payuHashIterator.hasNext()){
                String key = payuHashIterator.next();
                switch (key){
                    case "payment_hash":
                        payuHashes.setPaymentHash(response.getString(key));
                        break;
                    case "get_merchant_ibibo_codes_hash": //
                        payuHashes.setMerchantIbiboCodesHash(response.getString(key));
                        break;
                    case "vas_for_mobile_sdk_hash":
                        payuHashes.setVasForMobileSdkHash(response.getString(key));
                        break;
                    case "payment_related_details_for_mobile_sdk_hash":
                        payuHashes.setPaymentRelatedDetailsForMobileSdkHash(response.getString(key));
                        break;
                    case "delete_user_card_hash":
                        payuHashes.setDeleteCardHash(response.getString(key));
                        break;
                    case "get_user_cards_hash":
                        payuHashes.setStoredCardsHash(response.getString(key));
                        break;
                    case "edit_user_card_hash":
                        payuHashes.setEditCardHash(response.getString(key));
                        break;
                    case "save_user_card_hash":
                        payuHashes.setSaveCardHash(response.getString(key));
                        break;
                    case "check_offer_status_hash":
                        payuHashes.setCheckOfferStatusHash(response.getString(key));
                        break;
                    case "check_isDomestic_hash":
                        payuHashes.setCheckIsDomesticHash(response.getString(key));
                        break;
                    default:
                        break;
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return payuHashes;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
