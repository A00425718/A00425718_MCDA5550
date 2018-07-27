package com.example.jyoth.bmi_calculator;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class calculate extends AppCompatActivity {
    InClassDatabaseHelper helper;
    Button calculateBMIButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);
        helper = new InClassDatabaseHelper(this);
    }

    public void calculate(View view){

        //gets the height
        EditText height = (EditText)findViewById(R.id.Height);
        String value =  height.getText().toString();
        Double heightVal = Double.parseDouble(value);
        System.out.println("Here is the Height"+heightVal);

        //gets the weight
        EditText weight = (EditText)findViewById(R.id.Weight);
        String value1 =  height.getText().toString();
        Double weightVal = Double.parseDouble(value1);
        System.out.println("Here is the Weight "+ weightVal);


        double calc = (weightVal/(heightVal * heightVal));
        EditText result = (EditText)findViewById(R.id.result);
       // TextView resultdisplay = (TextView) findViewById(R.id.resultdisplay);

        //Use DecimalFormat("0.##") to limit to 2 decimal places

      //  result.setText(calc.toString);
        displayBMI(calc);
        helper.saveBMIData(heightVal,weightVal,calc);
    }


    private void displayBMI(double calc){
        String bmiLabel = "";
        TextView resultdisplay = (TextView) findViewById(R.id.resultdisplay);
        if (Float.compare((float) calc,15f) <= 0){
            bmiLabel = "Very severely underweight";
        }else if (Float.compare((float) calc,15f) > 0 && Float.compare((float) calc,16f) <= 0) {
            bmiLabel = "severely underweight";
        }else if(Float.compare((float) calc,16f) > 0 && Float.compare((float) calc,18.5f) <= 0){
            bmiLabel = "underweight";
        }else if(Float.compare((float) calc,18.5f) > 0 && Float.compare((float) calc,25f) <= 0){
            bmiLabel = "normal";
        }else if(Float.compare((float) calc,25f) > 0 && Float.compare((float) calc,30f) <= 0){
            bmiLabel = "overweight";
        }else if(Float.compare((float) calc,30f) > 0 && Float.compare((float) calc,35f) <= 0){
            bmiLabel = "obese class I";
        }else if(Float.compare((float) calc,35f) > 0 && Float.compare((float) calc,40f) <= 0){
            bmiLabel = "obese class II";
        }else{
            bmiLabel = "obese class III";
        }
        bmiLabel = calc + "\n" + bmiLabel;
        resultdisplay.setText(bmiLabel);
    }
}
