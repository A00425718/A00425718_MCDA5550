package com.example.jyoth.bmiapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;

public class InClassDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "inclass";  // name of the DB
    private static final int DB_VERSION = 1;  // version of the DB
    public static final String TABLE_NAME = "PERSON";  // name of the Table

    public InClassDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);  //   null is for cursors

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+" ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "PASSWORD TEXT, "   // Never store passwords in clear text in real apps
                    + "HEALTH_CARD_NUMB TEXT, "
                    + "DATE INTEGER);");
        Date today = new Date();        // we want to start with some initial data
        ContentValues personValues= new ContentValues();
        personValues.put("NAME", "Jyothi Sethi");
        personValues.put("PASSWORD", 1234);
        personValues.put("HEALTH_CARD_NUMBER", 121234564);
        personValues.put("DATE", today.getTime());

        db.insert(TABLE_NAME,null,personValues);

        }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
