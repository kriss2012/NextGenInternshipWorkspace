package com.nextgen.week2.calculator;

/**
 * Functional Interface representing a mathematical operation.
 */
@FunctionalInterface
public interface Operation {
    double execute(double a, double b) throws ArithmeticException;
}
