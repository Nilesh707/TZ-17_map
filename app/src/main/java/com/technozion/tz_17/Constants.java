package com.technozion.tz_17;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by A on 3/8/2016.
 */

public class Constants {
    public static final String APP_PREFERENCE = "app_preference";
    public static final String INVALID_TOKEN = "Invalid Token";
    public static final String NO_TOKEN = "No Token";
    public static final String TOKEN_STR = "token_string";
    public static String TOKEN;
    public static String isSignedIN = "isSignedIn";
    public static String tzid = "tzid";
    public static final String username = "username";
    public static final String email = "email";
    public static final String regfee = "regfee";
    public static final String hospfee = "hospfee";

    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;
        if (height > reqHeight) {
            inSampleSize = Math.round((float) height / (float) reqHeight);
        }
        int expectedWidth = width / inSampleSize;
        if (expectedWidth > reqWidth) {
            inSampleSize = Math.round((float) width / (float) reqWidth);
        }
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static String LoginState(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.APP_PREFERENCE, context.MODE_PRIVATE);
        String signin = sharedPreferences.getString(Constants.isSignedIN, null);
        return signin;
    }

    public static String getUsername(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.APP_PREFERENCE, context.MODE_PRIVATE);
        String username = sharedPreferences.getString(Constants.username, null);
        return username;
    }

    public static String getEmail(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.APP_PREFERENCE, context.MODE_PRIVATE);
        String email = sharedPreferences.getString(Constants.email, null);
        return email;
    }

    public static String gettz_id(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.APP_PREFERENCE, context.MODE_PRIVATE);
        String tzid = sharedPreferences.getString(Constants.tzid, null);
        return tzid;
    }

    public static String getRegStatus(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.APP_PREFERENCE, context.MODE_PRIVATE);
        String signin = sharedPreferences.getString(Constants.regfee, null);
        return signin;
    }

    public static String getHospStatus(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.APP_PREFERENCE, context.MODE_PRIVATE);
        String signin = sharedPreferences.getString(Constants.hospfee, null);
        return signin;
    }



}
