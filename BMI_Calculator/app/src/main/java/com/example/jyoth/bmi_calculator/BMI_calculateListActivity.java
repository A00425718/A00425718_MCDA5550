package com.example.jyoth.bmi_calculator;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class BMI_calculateListActivity extends AppCompatActivity {

    private int currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmilist);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            currentUserId = b.getInt("current_user");
        }

        // instantiate the db helper class
        InClassDatabaseHelper helper = new InClassDatabaseHelper(this);

        ArrayList<DisplayBMI> results = helper.getBMIResultsByPersonId(currentUserId);

        if (results.size() != 0) {
            ListView bmiResultsList = (ListView)findViewById(R.id.bmi_history_list);

            BMIListAdapter listAdapter = new BMIListAdapter(this, 0, results);

            bmiResultsList.setAdapter(listAdapter);
        }
        else {
            Toast.makeText(this, "No BMI results to show", Toast.LENGTH_SHORT).show();
        }

    }

    public void onListItemClick(ListView listView, View itemView, int position, long id) {

    }

}