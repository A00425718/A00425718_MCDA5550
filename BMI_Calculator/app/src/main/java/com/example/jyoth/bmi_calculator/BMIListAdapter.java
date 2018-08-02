package com.example.jyoth.bmi_calculator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class BMIListAdapter extends ArrayAdapter<DisplayBMI>{

    public BMIListAdapter(@NonNull Context context, int resource, ArrayList<DisplayBMI> results) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        System.out.println("position is  " +position);
        DisplayBMI result = getItem(position);
        if(convertView == null)  {convertView = LayoutInflater.from(getContext()).inflate(R.layout.display_bmi_row, parent, false);}

        TextView dateRecord = (TextView) convertView.findViewById(R.id.bmi_history_date);
        TextView heightRecord = (TextView) convertView.findViewById(R.id.bmi_history_height);
        TextView weightRecord = (TextView) convertView.findViewById(R.id.bmi_history_weight);
        TextView bmiRecord = (TextView) convertView.findViewById(R.id.bmi_history_bmi);

        Date date = new Date(result.getDate());
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        dateRecord.setText("Date: " +  dateFormat.format(date));
        heightRecord.setText("Height: " + String.valueOf(result.getHeight()));
        weightRecord.setText("Weight: " + String.valueOf(result.getWeight()));

        DecimalFormat df = new DecimalFormat("####0.00");
        String formattedResult = df.format(result.getBmi());
        bmiRecord.setText("BMI: " + formattedResult);

        return convertView;
    }
}


