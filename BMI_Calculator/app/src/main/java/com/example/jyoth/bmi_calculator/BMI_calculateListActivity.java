package com.example.jyoth.bmi_calculator;


import android.app.Activity;

import android.app.ListActivity;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.view.View;

import android.view.ViewGroup;

import android.widget.ArrayAdapter;

import android.widget.BaseAdapter;

import android.widget.ListView;

import android.widget.TextView;

import android.widget.Toast;


import java.util.ArrayList;



public class BMI_calculateListActivity extends AppCompatActivity {

    private int currentUserId;
    ArrayList<DisplayBMI> results;

  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmilist);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            currentUserId = b.getInt("current_user");
        }

        
        InClassDatabaseHelper helper = new InClassDatabaseHelper(this);

		results = helper.getBMIResultsByPersonId(currentUserId);

        if (results.size() != 0) {
            ListView bmiResultsList = (ListView)findViewById(R.id.bmi_history_list);

          //  BMIListAdapter listAdapter = new BMIListAdapter(this, 0, results);

         //   bmiResultsList.setAdapter(listAdapter);
		 
		 BMiAdapter adapter = new BMiAdapter();
		 bmiResultsList.setAdapter(adapter);

        }
        else {
            Toast.makeText(this, "No BMI results to show", Toast.LENGTH_SHORT).show();
        }

    }
	
	
	class BMiAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return results.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.display_bmi_row, null);
            TextView heightView = view.findViewById(R.id.bmi_history_height);
            TextView weightView = view.findViewById(R.id.bmi_history_weight);
            TextView bmiView = view.findViewById(R.id.bmi_history_bmi);
            TextView dateView = view.findViewById(R.id.bmi_history_date);
            heightView.setText(String.valueOf(results.get(i).getHeight()));
            weightView.setText(String.valueOf(results.get(i).getWeight()));
            bmiView.setText(String.valueOf(results.get(i).getBmi()));
            dateView.setText(String.valueOf(results.get(i).getDate()));


            return view;
        }
    }

   


    public void onListItemClick(ListView listView, View itemView, int position, long id) {

    }

}