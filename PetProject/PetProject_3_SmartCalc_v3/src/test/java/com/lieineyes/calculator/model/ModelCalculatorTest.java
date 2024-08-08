package com.lieineyes.calculator.model;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ModelCalculatorTest {

    ModelCalculator modelCalculator = new ModelCalculator();

    @Test
    void calculate() {
        String expression = "3+3+x";
        String valueX = "3";
        Object[] result = modelCalculator.calculate(expression, valueX);
        assertEquals(9.0, result[0]);
    }

    @Test
    void validInputExpressionCorrect() {
        String expression = "ln2+asin2";
        String result = modelCalculator.validInputExpression(expression);
        assertEquals("0", result);
    }

    @Test
    void validInputExpressionIncorrect() {
        String expression = "ln2+/asin2";
        String result = modelCalculator.validInputExpression(expression);
        assertNotEquals("0", result);
    }

}
