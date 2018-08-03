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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    public static int CURRENT_USER = 0;
    private Boolean isNewUser = false;

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




        resultDateofBirth.setOnClickListener(this);
        SubmitButton.setOnClickListener(this);



        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        Bundle b = getIntent().getExtras();
        helper = new InClassDatabaseHelper(this);
       SQLiteDatabase db= helper.getWritableDatabase();
        if (!isNewUser) {
            retrieveUserDetails(CURRENT_USER);
            SubmitButton.setText("Update & Continue");
        }


      //  CURRENT_USER = b.getInt("current_user");
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


    }

    public void retrieveUserDetails(int currentUserId){
        //run a query to retrieve existing user details

        SQLiteDatabase db = helper.getWritableDatabase();

        Cursor cursor = db.query(InClassDatabaseHelper.TABLE_NAME,
                new String[] {"_id","NAME","PASSWORD","HEALTH_CARD_NUMB","DATE_OF_BIRTH"},
                "_id=?",new String[] {String.valueOf(currentUserId)},null,null,null);

        if (cursor.moveToFirst()) {
            CURRENT_USER = cursor.getInt(0);
            String name = cursor.getString(1);
            String password = cursor.getString(2);
            String healthCardNumber = cursor.getString(3);

            Date date = new Date(cursor.getLong(4));
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String dateOfBirth = dateFormat.format(date);

            resultName.setText(name);
            resultPassword.setText(password);
            resultDateofBirth.setText(date.toString());
            resultHealthCardNumber.setText(healthCardNumber);


        }

        cursor.close();
        db.close();
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
                            if(submit == true){
                                Toast.makeText(MainActivity.this,"Registered Successfully",Toast.LENGTH_LONG).show();
                                try {
                                    Intent myintent = new Intent(MainActivity.this,LoginActivity.class);
                                    Bundle b = new Bundle();
                                    b.putString("Submit", String.valueOf(submit));
                                    myintent.putExtras(b);
                                    startActivity(myintent);
                                }catch (Exception ex) {
                                    String s = ex.getMessage();
                                }
                            }
                        }


                    break;

                     }

        }


}