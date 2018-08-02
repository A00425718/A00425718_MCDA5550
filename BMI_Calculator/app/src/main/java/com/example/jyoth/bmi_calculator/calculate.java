package com.example.jyoth.bmi_calculator;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import static com.example.jyoth.bmi_calculator.InClassDatabaseHelper.TABLE_NAME;
import static com.example.jyoth.bmi_calculator.InClassDatabaseHelper.TABLE_NAME1;

public class calculate extends AppCompatActivity {
    InClassDatabaseHelper helper;
    Button calculateBMIButton;

    Button btnbmiviewAll;
    Button btnbmiviewUpdate;
    Button buttonBMIupdate;
    private int currentUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);

    //   currentUserId = Integer.parseInt(getIntent().getStringExtra("Current_user"));
        Bundle b = getIntent().getExtras();
//       currentUserId = b.getInt("current_user");
        helper = new InClassDatabaseHelper(this);

        btnbmiviewAll = (Button)findViewById(R.id.buttonBMIview);

    }

    public void calculate(View view){
        String bmiLabel = "BMI Calculation";
        //gets the height
        EditText height = (EditText)findViewById(R.id.Height);

        String value =  height.getText().toString();

        Double heightVal = Double.parseDouble(value);
        System.out.println("Here is the Height"+heightVal);

        //gets the weight
        EditText weight = (EditText)findViewById(R.id.Weight);

        String value1 =  weight.getText().toString();
        Double weightVal = Double.parseDouble(value1);
        System.out.println("Here is the Weight "+ weightVal);


        double calc = (weightVal/(heightVal * heightVal));
        EditText result = (EditText)findViewById(R.id.result);

        TextView resultdisplay = (TextView) findViewById(R.id.resultdisplay);
        if ( calc < 16){
            bmiLabel = "Very severely Thinness";
        }else if (calc >= 16 && calc <= 17) {
            bmiLabel = "Moderate Thinness";
        }else if(calc > 17 && calc <= 18.5){
            bmiLabel = "Mild Thinness";
        }else if(calc > 18.5 && calc <= 25){
            bmiLabel = "normal";
        }else if(calc > 25 && calc <= 30){
            bmiLabel = "Overweight";
        }else if(calc > 30 && calc <= 35){
            bmiLabel = "obese class I";
        }else if(calc > 35 && calc <= 40){
            bmiLabel = "obese class II";
        }else{
            bmiLabel = "obese class III";
        }
        bmiLabel = calc + "\n" + bmiLabel;
        resultdisplay.setText(bmiLabel);

        helper.saveBMIData(currentUserId,heightVal,weightVal,calc,bmiLabel);
    }

    public void onHistoryClick(View view){
        Intent intent = new Intent(this,BMI_calculateListActivity.class);
        Bundle b = new Bundle();
        b.putInt("current_user", currentUserId);
        intent.putExtras(b);
        startActivity(intent);
    }


}


