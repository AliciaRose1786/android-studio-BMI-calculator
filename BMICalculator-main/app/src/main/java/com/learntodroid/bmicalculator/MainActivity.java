package com.learntodroid.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import static com.learntodroid.bmicalculator.BMICalcUtil.BMI_CATEGORY_HEALTHY;
import static com.learntodroid.bmicalculator.BMICalcUtil.BMI_CATEGORY_OBESE;
import static com.learntodroid.bmicalculator.BMICalcUtil.BMI_CATEGORY_OVERWEIGHT;
import static com.learntodroid.bmicalculator.BMICalcUtil.BMI_CATEGORY_SEVOBESE;
import static com.learntodroid.bmicalculator.BMICalcUtil.BMI_CATEGORY_UNDERWEIGHT;
import static com.learntodroid.bmicalculator.BMICalcUtil.BMI_CATEGORY_VERYOBESE;

public class MainActivity extends AppCompatActivity {
    private EditText weightKgEditText, heightCmEditText;
    private EditText weightLbsEditText, heightFtEditText, heightInEditText;
    private Button calculateButton;
    private TextView bmiTextView, categoryTextView;
    private ToggleButton toggleUnitsButton;
    private CardView bmiResultCardView;

    private Button aboutButton;

    private boolean inMetricUnits;

    SharedPreferences sharedPref;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        weightKgEditText = findViewById(R.id.activity_main_weightkgs);
        heightCmEditText = findViewById(R.id.activity_main_heightcm);

        weightLbsEditText = findViewById(R.id.activity_main_weightlbs);
        heightFtEditText = findViewById(R.id.activity_main_heightfeet);
        heightInEditText = findViewById(R.id.activity_main_heightinches);

        calculateButton = findViewById(R.id.activity_main_calculate);
        toggleUnitsButton = findViewById(R.id.activity_main_toggleunits);
        aboutButton = (Button) findViewById(R.id.about_button);

        bmiTextView = findViewById(R.id.activity_main_bmi);
        categoryTextView = findViewById(R.id.activity_main_category);
        bmiResultCardView = findViewById(R.id.activity_main_resultcard);

        //load the data

        sharedPref = this.getSharedPreferences("Previous BMI", Context.MODE_PRIVATE);
        sharedPref.getString("Previous BMI", "Error");

        //end load data



        inMetricUnits = true;
        updateInputsVisibility();
        bmiResultCardView.setVisibility(View.GONE);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inMetricUnits) {
                    if (weightKgEditText.length() == 0 || heightCmEditText.length() == 0) {
                        Toast.makeText(MainActivity.this, "Please Input Weight and Height to Calculate BMI", Toast.LENGTH_LONG).show();
                    } else {
                        double heightInCms = Double.parseDouble(heightCmEditText.getText().toString());
                        double weightInKgs = Double.parseDouble(weightKgEditText.getText().toString());
                        double bmi = BMICalcUtil.getInstance().calculateBMIMetric(heightInCms, weightInKgs);
                        displayBMI(bmi);

                        //saving the data

                        SharedPreferences.Editor editor = sharedPref.edit();
                        //editor.putInt("Previous BMI", bmi);
                        String b = Double.toString(bmi);
                        editor.putString("Previous BMI", b);
                        editor.commit();
                    }
                } else {
                    if (weightLbsEditText.length() == 0 || heightFtEditText.length() == 0 || heightInEditText.length() == 0) {
                        Toast.makeText(MainActivity.this, "Input Weight and Height to Calculate BMI", Toast.LENGTH_LONG).show();
                    } else {
                        double heightFeet = Double.parseDouble(heightFtEditText.getText().toString());
                        double heightInches = Double.parseDouble(heightInEditText.getText().toString());
                        double weightLbs = Double.parseDouble(weightLbsEditText.getText().toString());
                        double bmi = BMICalcUtil.getInstance().calculateBMIImperial(heightFeet, heightInches, weightLbs);
                        displayBMI(bmi);

                        //saving data
                        SharedPreferences.Editor editor = sharedPref.edit();
                        String b = Double.toString(bmi);
                        editor.putString("Previous BMI", b);
                        editor.apply();
                    }
                }
            }
        });

        //about page here
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, "About page", Toast.LENGTH_SHORT).show();
               // Intent intent = new Intent(this, AboutActivity.class);
              //  startActivity(intent);
                openActivityAbout();
            }
        });



        //about page end here

        toggleUnitsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inMetricUnits = !inMetricUnits;
                updateInputsVisibility();
            }
        });
    }



    public void openActivityAbout(){
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    private void updateInputsVisibility() {
        if (inMetricUnits) {
            heightCmEditText.setVisibility(View.VISIBLE);
            weightKgEditText.setVisibility(View.VISIBLE);
            heightFtEditText.setVisibility(View.GONE);
            heightInEditText.setVisibility(View.GONE);
            weightLbsEditText.setVisibility(View.GONE);
        } else {
            heightCmEditText.setVisibility(View.GONE);
            weightKgEditText.setVisibility(View.GONE);
            heightFtEditText.setVisibility(View.VISIBLE);
            heightInEditText.setVisibility(View.VISIBLE);
            weightLbsEditText.setVisibility(View.VISIBLE);
        }
    }

    private void displayBMI(double bmi) {
        bmiResultCardView.setVisibility(View.VISIBLE);

        bmiTextView.setText(String.format("%.2f", bmi));

        String bmiCategory = BMICalcUtil.getInstance().classifyBMI(bmi);
        //String bmiRisk = BMICalcUtil.getInstance().classifyBMI(bmi);
        //riskTextView.setText(bmiRisk);
        categoryTextView.setText(bmiCategory);

        SharedPreferences.Editor editor = sharedPref.edit();
        String b = Double.toString(bmi);
        editor.putString("Previous BMI", b);
        editor.apply();

        switch (bmiCategory) {
            case BMI_CATEGORY_UNDERWEIGHT:
                bmiResultCardView.setCardBackgroundColor(Color.LTGRAY);
                break;
            case BMI_CATEGORY_HEALTHY:
                bmiResultCardView.setCardBackgroundColor(Color.GREEN);
                break;
            case BMI_CATEGORY_OVERWEIGHT:
                bmiResultCardView.setCardBackgroundColor(Color.YELLOW);
                break;
            case BMI_CATEGORY_OBESE:
                bmiResultCardView.setCardBackgroundColor(Color.DKGRAY);
                break;
            case BMI_CATEGORY_SEVOBESE:
                bmiResultCardView.setCardBackgroundColor(Color.MAGENTA);
                break;
            case BMI_CATEGORY_VERYOBESE:
                bmiResultCardView.setCardBackgroundColor(Color.RED);
                break;
        }
    }
}