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

       // currentUserId = Integer.parseInt(getIntent().getStringExtra("Current_user"));

        helper = new InClassDatabaseHelper(this);

        btnbmiviewAll = (Button)findViewById(R.id.buttonBMIview);
      //  btnbmiviewUpdate = (Button)findViewById(R.id.buttonBMIupdate);
    }

    public void calculate(View view){
        String bmiLabel = "BMI Calculation";
        //gets the height
        EditText height = (EditText)findViewById(R.id.Height);
        height.setFilters(new InputFilter[]{ new InputFilterMinMax("58", "76")});
        String value =  height.getText().toString();

        Double heightVal = Double.parseDouble(value);
        System.out.println("Here is the Height"+heightVal);

        //gets the weight
        EditText weight = (EditText)findViewById(R.id.Weight);
        weight.setFilters(new InputFilter[]{ new InputFilterMinMax("91", "443")});
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
       // Date today = new Date();

       // TextView resultdisplay = (TextView) findViewById(R.id.resultdisplay);

        //Use DecimalFormat("0.##") to limit to 2 decimal places

      //  result.setText(calc.toString);
      //  displayBMI(calc,bmiLabel);
        helper.saveBMIData(heightVal,weightVal,calc,bmiLabel);
    }


      public void ViewAllBMIClicked(View v) {
        Cursor res = helper.getAllBMIData();
        if(res.getCount() == 0){
            showMessage("Error","No Data Found");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()){
            buffer.append("ID :" + res.getString(0)+"\n");
            buffer.append("HEIGHT :" + res.getString(1)+"\n");
            buffer.append("WEIGHT :" + res.getString(2)+"\n");
            buffer.append("BMIRESULT :" + res.getString(3)+"\n");
            buffer.append("BMILABEL :" + res.getString(4)+"\n");
            buffer.append("DATE_TIME :" + res.getInt(5)+"\n\n");
        }

        //show all data
        showMessage("Data",buffer.toString());
    }





    public void showMessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


    public class InputFilterMinMax implements InputFilter {

        private int min, max;

        public InputFilterMinMax(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public InputFilterMinMax(String min, String max) {
            this.min = Integer.parseInt(min);
            this.max = Integer.parseInt(max);
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                // Remove the string out of destination that is to be replaced
                String newVal = dest.toString().substring(0, dstart) + dest.toString().substring(dend, dest.toString().length());
                // Add the new string in
                newVal = newVal.substring(0, dstart) + source.toString() + newVal.substring(dstart, newVal.length());
                int input = Integer.parseInt(newVal);
                if (isInRange(min, max, input))
                    return null;
            } catch (NumberFormatException nfe) { }
            return "";
        }

        private boolean isInRange(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }




}
