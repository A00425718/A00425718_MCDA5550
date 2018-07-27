package com.example.jyoth.bmi_calculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;

import java.util.Date;

public class InClassDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "inclass";  // name of the DB
    private static final int DB_VERSION = 1;  // version of the DB
    public static final String TABLE_NAME = "PERSON";  // name of the Table
    public static final String TABLE_NAME1 = "BMIDetails";
    public InClassDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);  //   null is for cursors

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "NAME TEXT, "
                + "PASSWORD TEXT, "   // Never store passwords in clear text in real apps
                + "DATE TEXT, "
                + "HEALTH_CARD_NUMB TEXT);");

        db.execSQL("CREATE TABLE " + TABLE_NAME1 + " ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "HEIGHT DOUBLE, "
                + "WEIGHT DOUBLE, "   // Never store passwords in clear text in real apps
                + "BMIRESULT DOUBLE);");
    }


    public  void savePersonData(String name ,String password, String healthCardNumber, String date ){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues personValues= new ContentValues();
        personValues.put("NAME", name);
        personValues.put("PASSWORD", password);
        personValues.put("DATE", date);
        personValues.put("HEALTH_CARD_NUMB", healthCardNumber);

        db.insert(TABLE_NAME,null, personValues);

        db.close();
    }

    public  void saveBMIData(Double height ,Double weight,Double BMIResult ){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues bmiValues = new ContentValues();
        bmiValues.put("HEIGHT", height);
        bmiValues.put("WEIGHT", weight);
        bmiValues.put("BMIRESULT", BMIResult);
        db.insert(TABLE_NAME1,null, bmiValues);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE EXISTS" + TABLE_NAME);
        onCreate(db);
    }

    public Cursor getAllData(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME,null);
        return res;

    }

    public boolean updateData(String id,String name,String password,String date,String healthCardNumber){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues personValues= new ContentValues();
        //personValues.put("ID", id);
        personValues.put("NAME", name);
        personValues.put("PASSWORD", password);
        personValues.put("DATE", date);
        personValues.put("HEALTH_CARD_NUMB", healthCardNumber);
        db.update(TABLE_NAME,personValues,"_id = ?", new String[] { id});
        return true;
    }


}