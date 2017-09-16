package com.technozion.tz_17;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


public class VideosDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tz_videos.db";
    private final String TAG = "VideosDbHelper";
    final String videosTableName = "youtube_videos";
    SQLiteDatabase db;

    public VideosDbHelper(Context context) {
        super(context, DATABASE_NAME, null, 4);
        db = getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table " + videosTableName + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "youtubeId text NOT NULL)";
        Log.d(TAG, "DB : " + query);
        db.execSQL(query);
    }

    @Override
    /*
    This methods run only when there is change in version from lower to upper level.
    through the constructor.
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("delete from " + videosTableName);
        db.execSQL("alter table " + videosTableName + " add column videoTitle text");
    }

    public boolean insertVideo(String videoId, String title){
        videoId = videoId.trim().replace("'", "''");
        title = title.trim().replace("'", "''");

        String query = "insert into " + videosTableName + " (youtubeId, videoTitle) values ('"+videoId+"' , '" + title + "')";
        Log.d(TAG, "DB : " + query);
        db.execSQL(query);
        return true;
    }

    public String[] getVideoIds(){
        String query = "select * from " + videosTableName;
        Log.d(TAG, "DB : " + query);
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<String> videos = new ArrayList<>();
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            do {
                videos.add(cursor.getString(1));
            }while(cursor.moveToNext());
        }
        String videosArray[] = new String[videos.size()];
        for(int i=0; i<videos.size(); i++)
            videosArray[i] = videos.get(i);
        return videosArray;
    }

    public String[] getVideoTitles(){
        String query = "select * from " + videosTableName;
        Log.d(TAG, "DB : " + query);
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<String> videos = new ArrayList<>();
        cursor.moveToFirst();
        if(cursor.getCount()>0){
            do {
                videos.add(cursor.getString(2));
            }while(cursor.moveToNext());
        }
        String videosArray[] = new String[videos.size()];
        for(int i=0; i<videos.size(); i++)
            videosArray[i] = videos.get(i);
        return videosArray;
    }

}

