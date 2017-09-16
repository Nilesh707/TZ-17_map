package com.technozion.tz_17;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

/**
 * Created by A on 2/14/2016.
 */
public class Database  extends SQLiteAssetHelper {
    private static final String DATABASE_PATH="/data/data/com.technozion.tz_17/databases/";
    private static final String DATABASE_NAME = "Events.db";
    private static final int DATABASE_VERSION = 1;
    public Database(Context context) {

        super(context, DATABASE_NAME, DATABASE_PATH, null, DATABASE_VERSION);
    }

    public SQLiteDatabase create_db(){
        SQLiteDatabase db;
        db=getReadableDatabase();
        return db;
    }
}