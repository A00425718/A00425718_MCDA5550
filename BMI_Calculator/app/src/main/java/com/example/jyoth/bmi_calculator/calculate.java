package com.example.jyoth.bmi_calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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


        Double calc = (weightVal/(heightVal * heightVal));
        EditText result = (EditText)findViewById(R.id.result);

        //Use DecimalFormat("0.##") to limit to 2 decimal places

        result.setText(calc.toString());

        helper.saveBMIData(heightVal,weightVal,calc);
    }


}
