package com.technozion.tz_17.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * Created by paras on 26/08/17.
 */

public class InstanceIdService extends FirebaseInstanceIdService {

    private final String TAG = "InstanceIdService";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        //Register token to server for handling the notifications on server side.
        // sendRegistrationToServer(refreshedToken);

        FirebaseMessaging.getInstance().subscribeToTopic("tz"); //subscribe to topic
    }

}
