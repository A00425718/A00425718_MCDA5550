package com.example.jyoth.bmiapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        InClassDatabaseHelper helper = new InClassDatabaseHelper(this);
        SQLiteDatabase db= helper.getWritableDatabase();

        // run a query
        Cursor cursor = db.query(InClassDatabaseHelper.TABLE_NAME,new String[]
        {"NAME","PASSWORD","DATE"},
        null,null,null,null,null);
        if (cursor.moveToFirst()){
            String name = cursor.getString(0);
            String DOB = cursor.getString(1);
            String date = cursor.getString(2);

            EditText results = (EditText) findViewById(R.id.Name);
            EditText result1 = (EditText) findViewById(R.id.DOB);
            EditText result2 = (EditText) findViewById(R.id.password);
            results.setText(name);
            result1.setText(DOB);
            result2.setText(date);

        }
        cursor.close();  // cleanup
        db.close();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickEnter(View view){
        Intent intent = new Intent(this,calculate.class);
        startActivity(intent);
    }
}
