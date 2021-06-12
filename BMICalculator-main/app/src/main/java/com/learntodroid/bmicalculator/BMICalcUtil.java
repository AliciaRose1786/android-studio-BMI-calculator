package com.learntodroid.bmicalculator;

public class BMICalcUtil {
    public static final BMICalcUtil instance = new BMICalcUtil();

    private static final int CENTIMETERS_IN_METER = 100;
    private static final int INCHES_IN_FOOT = 12;
    private static final int BMI_IMPERIAL_WEIGHT_SCALAR = 703;

    public static final String BMI_CATEGORY_UNDERWEIGHT = "\n Underweight \n\n MALNUTRITION Risk";
    public static final String BMI_CATEGORY_HEALTHY = "\n Normal weight \n\n LOW Risk";
    public static final String BMI_CATEGORY_OVERWEIGHT = "\n Overweight \n\n ENHANCED Risk";
    public static final String BMI_CATEGORY_OBESE = "\n Moderately Obese \n\n MEDIUM Risk";
    public static final String BMI_CATEGORY_SEVOBESE = "\n Severely Obese \n\n HIGH Risk";
    public static final String BMI_CATEGORY_VERYOBESE = "\n Very Severely Obese \n\n VERY HIGH Risk";


    public static BMICalcUtil getInstance() {
        return instance;
    }

    public double calculateBMIMetric(double heightCm, double weightKg) {
        return (weightKg / ((heightCm / CENTIMETERS_IN_METER) * (heightCm / CENTIMETERS_IN_METER)));
    }

    public double calculateBMIImperial(double heightFeet, double heightInches, double weightLbs) {
        double totalHeightInInches = (heightFeet * INCHES_IN_FOOT) + heightInches;
        return (BMI_IMPERIAL_WEIGHT_SCALAR * weightLbs) / (totalHeightInInches * totalHeightInInches);
    }

    public String classifyBMI(double bmi) {
        if (bmi < 18.5) {
            return BMI_CATEGORY_UNDERWEIGHT ;//+ BMI_HEALTH_RISK_MALNUTRITION;
        } else if (bmi >= 18.5 && bmi < 25) {
            return BMI_CATEGORY_HEALTHY; //+ BMI_HEALTH_RISK_LOW;
        } else if (bmi >= 25 && bmi < 30){
            return BMI_CATEGORY_OVERWEIGHT; //+ BMI_HEALTH_RISK_ENHANCED;
        } else if (bmi >= 30 && bmi < 35) {
            return BMI_CATEGORY_OBESE; //+ BMI_HEALTH_RISK_MEDIUM;
        } else if (bmi >= 35 && bmi < 40){
            return BMI_CATEGORY_SEVOBESE; //+ BMI_HEALTH_RISK_HIGH;
        } else {
            return BMI_CATEGORY_VERYOBESE; //+ BMI_HEALTH_RISK_VERYHIGH;
        }
    }
}
