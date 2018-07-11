package com.example.jyoth.bmiapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class calculate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);
    }

    public void calculate(View view){

        //gets the height
        EditText height = (EditText)findViewById(R.id.textHeight);
        String value =  height.getText().toString();
        Double heightVal = Double.parseDouble(value);
        System.out.println("Here is the Height"+heightVal);

        //gets the weight
        EditText weight = (EditText)findViewById(R.id.textWeight);
        String value1 =  height.getText().toString();
        Double weightVal = Double.parseDouble(value1);
        System.out.println("Here is the Weight "+ weightVal);


        Double calc = (weightVal/(heightVal * heightVal));
        EditText result = (EditText)findViewById(R.id.resultText);

        //Use DecimalFormat("0.##") to limit to 2 decimal places

        result.setText(calc.toString());
    }
}
