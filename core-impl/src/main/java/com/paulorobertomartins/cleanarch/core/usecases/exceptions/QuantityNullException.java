package com.paulorobertomartins.cleanarch.core.usecases.exceptions;

public class QuantityNullException extends UseCaseException {

    public QuantityNullException() {
        super("Quantity cannot be null.");
    }
}
