package com.example.jyoth.bmi_calculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.jar.Attributes;

import static android.widget.Toast.LENGTH_SHORT;

public class InClassDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "inclass";  // name of the DB
    private static final int DB_VERSION = 1;  // version of the DB
    public static final String TABLE_NAME = "PERSON";  // name of the Table
    public static final String TABLE_NAME1 = "BMIDetails";
    public static final String TABLE_NAME2 = "EMAILDetails";
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

        db.execSQL("CREATE TABLE " + TABLE_NAME2 + " ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "USERNAME TEXT, "
                + "PASSWORD TEXT);");   // Never store passwords in clear text in real apps



        db.execSQL("CREATE TABLE " + TABLE_NAME1 + " ("
                + "currentUserId INTEGER not null, "
                + "HEIGHT DOUBLE, "
                + "WEIGHT DOUBLE, "   // Never store passwords in clear text in real apps
                + "BMIRESULT DOUBLE,"
                + "BMILABEL TEXT,"
                + "DATE INTEGER)");

    }


    public  boolean savePersonData(String name ,String password, String healthCardNumber, String date ){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues personValues= new ContentValues();
        personValues.put("NAME", name);
        personValues.put("PASSWORD", password);
        personValues.put("DATE", date);
        personValues.put("HEALTH_CARD_NUMB", healthCardNumber);

        db.insert(TABLE_NAME,null, personValues);
        return true;

    }


    public  boolean saveemailData(String username ,String password ){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues persondetails= new ContentValues();
        persondetails.put("USERNAME", username);
        persondetails.put("PASSWORD", password);
        db.insert(TABLE_NAME2,null, persondetails);
        return true;

    }

    //checking if email exists;
    public Boolean chkmail(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME2+ " where USERNAME =?",new String[]{username});
       if(res.getCount()>0)
           return true;
       else return false;
    }

    public boolean emailpassword(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME2+ " where USERNAME =? and PASSWORD =?",new String[]{username,password});
        if(res.getCount()>0)
            return false;
        else return true;
    }

    public  void saveBMIData(int currentUserId,Double height ,Double weight,Double BMIResult, String bmiLabel){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues bmiValues = new ContentValues();
        bmiValues.put("currentUserId", currentUserId);
        bmiValues.put("HEIGHT", height);
        bmiValues.put("WEIGHT", weight);
        bmiValues.put("BMIRESULT", BMIResult);
        bmiValues.put("BMILABEL", bmiLabel);
        Date today = new Date();
        bmiValues.put("DATE", today.getTime());



        db.insert(TABLE_NAME1,null, bmiValues);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE EXISTS" + TABLE_NAME);
        onCreate(db);
    }

    public ArrayList<UserData> getAllData() {
        ArrayList<UserData> array_list = new ArrayList<UserData>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(InClassDatabaseHelper.TABLE_NAME,
                new String[] {"_id","NAME","PASSWORD","DATE_OF_BIRTH","HEALTH_CARD_NUMB"},
                null,null,null,null,null);

//        Cursor cursor =  db.rawQuery( "select * from " + BMI_TABLE_NAME + "where personId="+personId+"", null );

        while (cursor.moveToNext()) {
            UserData userRecord = new UserData();
            userRecord.id = cursor.getInt(0);
            userRecord.name = cursor.getString(1);
            userRecord.password = cursor.getString(2);
            userRecord.healthCardNumber = cursor.getString(4);
            userRecord.dateOfBirth = cursor.getLong(3);
            array_list.add(userRecord);
        }

        cursor.close();

        return array_list;

    }

    public int getUserId(String fullName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(InClassDatabaseHelper.TABLE_NAME,
                new String[] {"_id"},
                "NAME=?", new String[]{fullName},null,null,null);

        int currentUserId = 0;
        if (cursor.moveToFirst()) {
            currentUserId = cursor.getInt(0);
        }
        cursor.close();
        return currentUserId;
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

//    public Cursor getAllBMIData() {
//        SQLiteDatabase db = getWritableDatabase();
 //       Cursor res1;
  //      res1 = db.rawQuery("select * from " + TABLE_NAME1, null);
 //       return res1;

 //   }
 public ArrayList<DisplayBMI> getBMIResultsByPersonId(int userId) {
     ArrayList<DisplayBMI> array_list = new ArrayList<DisplayBMI>();
     SQLiteDatabase db = this.getReadableDatabase();

     Cursor cursor = db.query(InClassDatabaseHelper.TABLE_NAME1,
             new String[] {"HEIGHT", "WEIGHT", "BMILABEL", "DATE"},
             "currentUserId = ?",new String[] {String.valueOf(userId)},null,null,null);

     while (cursor.moveToNext()) {
         DisplayBMI bmiRecord = new DisplayBMI();
         bmiRecord.setHeight(cursor.getDouble(0));
         bmiRecord.setWeight(cursor.getDouble(1));
         bmiRecord.setBMI(cursor.getDouble(2));
         bmiRecord.setDate(cursor.getLong(3));
         array_list.add(bmiRecord);
     }

     cursor.close();

     return array_list;
 }



}