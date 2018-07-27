package com.example.jyoth.bmi_calculator;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BMI_calculateListActivity extends ListActivity {

    BMIResult[] results = {new BMIResult(5.5, 100), new BMIResult(4.3, 156)};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmilist);

        ListView listBMIResults = getListView();
        ArrayAdapter<BMIResult> listAdapter = new ArrayAdapter<BMIResult>(this,

                android.R.layout.simple_list_item_1,
                results
        );

        listBMIResults.setAdapter(listAdapter);


    }

    //Add to Activity on CLick
    public  void onListItemClick(ListView listView, View itemView , int position , long id)
    {
        System.out.println("Clicked on" + results[position].toString());
    }

    public  class  BMIResult {
        private double height = 1;
        private  double weight = 1;
        // TO do add the Date

        public  BMIResult(double height , double weight ){
            this.height = height;
            this.weight = weight;
        }

        public  double getHeight(){
            return  height;
        }
        public  void  setHeight (double height){
            this.height = height;
        }

        public  double getWeight(){
            return  weight;
        }
        public  void  setWeight (double weight){
            this.weight = weight;
        }


        public  double getResult(){
            return  weight/(height*height);
        }

        @Override
        public String toString() {
            return String.valueOf(getResult());
        }

        public BMIResult(){

        }
    }
}