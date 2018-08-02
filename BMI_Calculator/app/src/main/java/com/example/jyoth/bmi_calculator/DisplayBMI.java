package com.example.jyoth.bmi_calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DisplayBMI {
    private double height = 1;
    private double weight = 1;
    private double bmi = 1;
    private long date = 1;
            public DisplayBMI(double height, double weight, double bmi) {
            this.height = height;
            this.weight = weight;
            this.bmi = bmi;
        }

        public double getHeight() {
            return height;
        }

        public double getWeight() {
            return weight;
        }

        public long getDate() {
            return date;
        }

        public double getBmi() {
            return bmi;
        }

        public void setDate(long date) {
            this.date = date;
        }

        public void setHeight(double height) {
            this.height = height;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public double getResult() {
            return weight / (height * height);
        }

        public String toString() {
            return String.valueOf(getResult());
        }

        public DisplayBMI() {

        }

        public void setBMI(double bmi) {
            this.bmi = bmi;
        }

}
