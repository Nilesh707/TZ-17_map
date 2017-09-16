package com.technozion.tz_17;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Kiran Konduru on 10/10/2016.
 */

public class WebPayment  extends Activity {
    Button b1;
    EditText ed1;

    private WebView wv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webpayment);


        WebView myWebView = (WebView)findViewById(R.id.webView_pay);
        myWebView.getSettings().setLoadsImagesAutomatically(true);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        Intent in = getIntent();
        String trans_id = in.getStringExtra("trans_id");
        String token = Constants.TOKEN;
        String hash = in.getStringExtra("hash");
        myWebView.loadUrl("http://technozion.org/payment/checkoutmobile/"+trans_id+"/"+token+"/"+hash);
        myWebView.setWebViewClient(new WebViewClient());

    }

}