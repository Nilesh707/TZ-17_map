package com.technozion.tz_17.firebase;

import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.technozion.tz_17.ApplicationController;
import com.technozion.tz_17.DataBaseHelper;
import com.technozion.tz_17.MainActivity;
import com.technozion.tz_17.R;
import com.technozion.tz_17.VideosDbHelper;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by paras on 26/08/17.
 */

public class MessagingService extends FirebaseMessagingService {

    private final String TAG = "MessagingService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "From: " + remoteMessage.getFrom());


        String imageUri = null;

        //Check if message contains data payload for imageUri
        if(remoteMessage.getData().size() > 0){
            imageUri = remoteMessage.getData().get("image");
        }

        if(remoteMessage.getData().get("videos") != null && !remoteMessage.getData().get("videos").equals("")){
            //it's a videos update
            addVideosToDb(remoteMessage);

        }

        // Check if message contains a notification payload. : MANDATORY CONDITION
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotificationToDevice(
                    remoteMessage.getNotification().getTitle(),
                    remoteMessage.getNotification().getBody(),
                    imageUri
            );
        }
    }

    private void sendNotificationToDevice(String title, String message, String imageUri){
        Bitmap bitmap = null;
        NotificationCompat.Builder notificationBuilder;
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //convert imageUri into Bitmap (Fetch image from network, ***the service already runs on bg thread)
        if(imageUri != null)
            bitmap = getBitmapFromUrl(imageUri);
        else{
            Log.d(TAG, "imageUri null");
        }

        notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.tz)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        notificationBuilder.setLargeIcon(largeIcon);


        if(bitmap!=null) {

            notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                    .bigPicture(bitmap));    //Notification pull down Image
        } else {
            Log.d(TAG, "bitmap null");
        }


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        //notify the android OS for notification
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

    }

    public Bitmap getBitmapFromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            Log.d(TAG, "Bitmap " + imageUrl);
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    private void addVideosToDb(RemoteMessage remoteMessage){
        VideosDbHelper db = ((ApplicationController)getApplication()).videoDb;
        db.insertVideo(remoteMessage.getData().get("videoId"), remoteMessage.getData().get("title"));
    }
}
