package com.lieineyes.calculator.model;

public class ModelCalculator {

    static {
        System.load(System.getenv("HOME") + "/Desktop/SmartCalc_v_3_0/lib/libcalculator.so");
    }

    public native Object[] calculate(String inputExpression, String valueX);
    public native String validInputExpression(String inputExpression);

}
