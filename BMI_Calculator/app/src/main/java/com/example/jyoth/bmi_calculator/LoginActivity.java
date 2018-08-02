package com.example.jyoth.bmi_calculator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

   InClassDatabaseHelper helper;

   @Override
    protected void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_login);

       final EditText Username = (EditText) findViewById(R.id.username);
       final EditText Password = (EditText) findViewById(R.id.password);
       final Button btnlogin = (Button) findViewById(R.id.buttonlogin);
       final Button btnregister = (Button) findViewById(R.id.buttonRegister);

       helper = new InClassDatabaseHelper(this);
       SQLiteDatabase db= helper.getWritableDatabase();

       btnlogin.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){

               String username = Username.getText().toString();
               String password = Password.getText().toString();

               if (username.equals("")||password.equals("")){
                   Toast.makeText(LoginActivity.this,"Fields cannot be empty",Toast.LENGTH_SHORT).show();
                   return;
               }
               else {
                 //  boolean submit = helper.saveemailData(username, password);
                   boolean emailpassword = helper.emailpassword(username,password);
                   if (emailpassword == true) {

                           Toast.makeText(LoginActivity.this, "Successfully LoggedIn", Toast.LENGTH_LONG).show();
                           try {
                               Intent registerIntent = new Intent(LoginActivity.this, calculate.class);
                          //  Bundle b = new Bundle();
                          //     b.putString("Submit", String.valueOf(submit));
                           //    registerIntent.putExtras(b);
                               startActivity(registerIntent);
                           } catch (Exception ex) {
                               String s = ex.getMessage();
                           }

                   }
                   else {
                       Toast.makeText(LoginActivity.this, "Wrong email or password", Toast.LENGTH_LONG).show();
                   }

               }
               }
       });


       btnregister.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v){

               String username = Username.getText().toString();
               String password = Password.getText().toString();

               boolean chkmail = helper.chkmail(username);
               if (chkmail == true) {

                   Toast.makeText(LoginActivity.this, "Email Already exists", Toast.LENGTH_LONG).show();
               }
               else{
                   if (username.equals("")||password.equals("")){
                       Toast.makeText(LoginActivity.this,"Fields cannot be empty",Toast.LENGTH_SHORT).show();
                       return;
                   }
                   else{
                       Intent registerIntent = new Intent(LoginActivity.this,MainActivity.class);
                       LoginActivity.this.startActivity(registerIntent);
                   }

               }


           }
       });
   }

}




