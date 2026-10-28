package com.nextgen.week3.exceptions;

/**
 * Custom Exception thrown when data input fails business validations.
 */
public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}
