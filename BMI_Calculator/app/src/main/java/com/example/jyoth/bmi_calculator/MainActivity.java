package com.example.jyoth.bmi_calculator;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat simpleDateFormat;

    InClassDatabaseHelper helper;
    EditText resultid;
    EditText resultName;
    EditText resultPassword;
    EditText resultHealthCardNumber;
    EditText resultDateofBirth;
    Button SubmitButton;
    Button btnviewAll;
    Button btnviewUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultid = (EditText) findViewById(R.id.id);
        resultName = (EditText) findViewById(R.id.Name);
        resultDateofBirth = (EditText) findViewById(R.id.DateOfBirth);
        resultPassword = (EditText) findViewById(R.id.Password);
        resultHealthCardNumber = (EditText) findViewById(R.id.HealthCardNumber);

        SubmitButton = (Button) findViewById(R.id.buttonSubmit);
        btnviewAll = (Button)findViewById(R.id.button222);
        btnviewUpdate = (Button)findViewById(R.id.button_update);

        resultDateofBirth.setOnClickListener(this);
        SubmitButton.setOnClickListener(this);

        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());


        helper = new InClassDatabaseHelper(this);
        SQLiteDatabase db= helper.getWritableDatabase();

        // run a query
        Cursor cursor = db.query(InClassDatabaseHelper.TABLE_NAME,new String[]
                        {"NAME","PASSWORD","DATE", "HEALTH_CARD_NUMB"},
                null,null,null,null,null);
        if (cursor.moveToFirst()){
            Log.d("Cursor", String.valueOf(cursor.getCount()));

            String name = cursor.getString(0);
            String password = cursor.getString(1);
            String date = cursor.getString(2);
            String healthCardNumber = cursor.getString(3);

           // resultid.setText(id);
            resultName.setText(name);
            resultPassword.setText(password);
            resultDateofBirth.setText(date.toString());
            resultHealthCardNumber.setText(healthCardNumber);

        }

        cursor.close();  // cleanup
        db.close();

        setDateOfBirthField();
//        viewAll();
        UpdateData();
    }

   public void UpdateData(){
        btnviewUpdate.setOnClickListener(
                new OnClickListener(){
                    @Override
                    public void onClick(View v){
                        boolean isUpdate = helper.updateData(resultid.getText().toString(),resultName.getText().toString(),
                                resultPassword.getText().toString(),resultDateofBirth.getText().toString(),resultHealthCardNumber.getText().toString());
                        if(isUpdate == true)
                            Toast.makeText(MainActivity.this,"Data Update",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_SHORT).show();

                    }
                }
        );
   }

    private void setDateOfBirthField() {
        Calendar calendar = Calendar.getInstance();
        resultDateofBirth.setFocusable(false);
        resultDateofBirth.setClickable(true);
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(selectedYear, selectedMonth, selectedDay);
                resultDateofBirth.setText(simpleDateFormat.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

    }

        @Override
        public void onClick(View view) {

            switch (view.getId()){
                case R.id.DateOfBirth:
                    datePickerDialog.setTitle("Select Date");
                    datePickerDialog.show();
                    break;

                case R.id.buttonSubmit:
                    resultName = (EditText) findViewById(R.id.Name);
                    resultPassword = (EditText) findViewById(R.id.Password);
                    resultDateofBirth = (EditText)findViewById(R.id.DateOfBirth);
                    resultHealthCardNumber = (EditText)findViewById(R.id.HealthCardNumber);
                    String name = resultName.getText().toString();
                    String password = resultPassword.getText().toString();
                    String healthCardNumber = resultHealthCardNumber.getText().toString();
                    String dateOfBirth = resultDateofBirth.getText().toString();
                    helper.savePersonData(name,password,healthCardNumber,dateOfBirth);
                    try {
                        Intent intent = new Intent(this, calculate.class);
                        startActivity(intent);
                    }catch (Exception ex) {
                        String s = ex.getMessage();
                    }

                    break;
            }

        }

//        public void viewAll(){
//              btnviewAll.setOnClickListener(
//                      new View.OnClickListener() {
//                          @Override
//                          public void ViewAllClicked(View v) {
//                              Cursor res = helper.getAllData();
//                              if(res.getCount() == 0){
//                                  showMessage("Error","No Data Found");
//                                  return;
//                              }
//
//                              StringBuffer buffer = new StringBuffer();
//                              while(res.moveToNext()){
//                                  buffer.append("NAME :" + res.getString(0)+"\n");
//                                  buffer.append("PASSWORD :" + res.getString(1)+"\n");
//                                  buffer.append("DATEofBirth :" + res.getString(2)+"\n");
//                                  buffer.append("Health Card Number :" + res.getString(3)+"\n\n");
//                              }
//
//                              //show all data
//                              showMessage("Data",buffer.toString());
//                          }
//                      }
//              );
//        }



    public void ViewAllClicked(View v) {
        Cursor res = helper.getAllData();
        if(res.getCount() == 0){
            showMessage("Error","No Data Found");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()){
            buffer.append("ID :" + res.getString(0)+"\n");
            buffer.append("NAME :" + res.getString(1)+"\n");
            buffer.append("PASSWORD :" + res.getString(2)+"\n");
            buffer.append("DATEofBirth :" + res.getString(3)+"\n");
            buffer.append("Health Card Number :" + res.getString(4)+"\n\n");
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

}