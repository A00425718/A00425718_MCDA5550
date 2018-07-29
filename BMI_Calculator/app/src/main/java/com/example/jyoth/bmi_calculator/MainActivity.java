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

import static com.example.jyoth.bmi_calculator.InClassDatabaseHelper.TABLE_NAME;

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
    Button btnselect;
    Button btndelete;
    String select;

    private static int CURRENT_USER = 0;

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
        btnselect = (Button)findViewById(R.id.buttonsearch);
        btndelete = (Button)findViewById(R.id.buttondelete);


        resultDateofBirth.setOnClickListener(this);
        SubmitButton.setOnClickListener(this);

       // btnselect.setOnClickListener(this);
      //  btndelete.setOnClickListener(this);

        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());


        helper = new InClassDatabaseHelper(this);
        SQLiteDatabase db= helper.getWritableDatabase();

        // run a query
        Cursor cursor = db.query(TABLE_NAME,new String[]
                        {"_id","NAME","PASSWORD","DATE", "HEALTH_CARD_NUMB"},
                null,null,null,null,null);
        if (cursor.moveToFirst()){
            Log.d("Cursor", String.valueOf(cursor.getCount()));

            CURRENT_USER = cursor.getInt(0);
            String name = cursor.getString(1);
            String password = cursor.getString(2);
            String date = cursor.getString(3);
            String healthCardNumber = cursor.getString(4);

            //resultid.setText(id);
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
                        //boolean isUpdate = helper.updateData(resultid.getText().toString(),resultName.getText().toString(),
                        //        resultPassword.getText().toString(),resultDateofBirth.getText().toString(),resultHealthCardNumber.getText().toString());

                        select = resultid.getText().toString();
                        if(select.equals(""))
                        {
                            Toast.makeText(MainActivity.this,"Please Enter Valid User Id",Toast.LENGTH_LONG).show();
                            return;

                        }

                        SQLiteDatabase db = helper.getWritableDatabase();
                        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where _id ='"+ select + "'",null);
                        if(res.moveToFirst())
                        {
                            if (resultName.equals("")||resultPassword.equals("")||resultDateofBirth.equals("")||resultHealthCardNumber.equals("")){
                                Toast.makeText(MainActivity.this,"Fields cannot be empty",Toast.LENGTH_LONG).show();
                                return;
                            }
                            else{
                                boolean isUpdate = helper.updateData(resultid.getText().toString(),resultName.getText().toString(),
                                        resultPassword.getText().toString(),resultDateofBirth.getText().toString(),resultHealthCardNumber.getText().toString());
                                try {
                                    Intent myintent = new Intent(MainActivity.this,calculate.class);
                                    Bundle b = new Bundle();
                                    b.putString("Update", String.valueOf(isUpdate));
                                    myintent.putExtras(b);
                                    startActivity(myintent);
                                }catch (Exception ex) {
                                    String s = ex.getMessage();
                                }
                                if(isUpdate == true)
                                    Toast.makeText(MainActivity.this,"Data Update",Toast.LENGTH_LONG).show();
                                else
                                    Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_SHORT).show();


                            }
                        }

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
                    if (name.equals("")||password.equals("")||healthCardNumber.equals("")||dateOfBirth.equals("")){
                        Toast.makeText(this,"Fields cannot be empty",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else {
                        boolean submit = helper.savePersonData(name, password, healthCardNumber, dateOfBirth);


                        try {
                            Intent myintent = new Intent(MainActivity.this,calculate.class);
                            Bundle b = new Bundle();
                            b.putString("Submit", String.valueOf(submit));
                            myintent.putExtras(b);
                            startActivity(myintent);
                        }catch (Exception ex) {
                            String s = ex.getMessage();
                        }

                        if(submit == true)
                            Toast.makeText(MainActivity.this,"Data Update",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_SHORT).show();





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

    public void ViewsearchClicked(View v){
        select = resultid.getText().toString();
        if(btnselect.equals("")){
            Toast.makeText(this,"Enter Name",Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            SQLiteDatabase db = helper.getWritableDatabase();
            Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where _id ='"+ select + "'",null);

            if(res.moveToFirst())
            {
                resultName.setText(res.getString(1));
                resultPassword.setText(res.getString(2));
                resultDateofBirth.setText(res.getString(3));
                resultHealthCardNumber.setText(res.getString(4));
                try {
                    Intent intent = new Intent(this, calculate.class);
                    startActivity(intent);
                } catch (Exception ex) {
                    String s = ex.getMessage();
                }
            }
            else
            {
                Toast.makeText(this,"Data Not Found",Toast.LENGTH_SHORT).show();
            }

        }
    }


    public void ViewdeleteClicked(View v){
        select = resultid.getText().toString();
        if(btndelete.equals("")){
            Toast.makeText(this,"Enter ID to delete",Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            SQLiteDatabase db = helper.getWritableDatabase();
            Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where _id ='"+ select + "'",null);

            if(res.moveToFirst())
            {
                db.execSQL("Delete * from " + TABLE_NAME + " where _id ='"+ select+ "'",null);
            }
            else
            {
                Toast.makeText(this,"Data Not Found",Toast.LENGTH_SHORT).show();
            }

        }
    }


}