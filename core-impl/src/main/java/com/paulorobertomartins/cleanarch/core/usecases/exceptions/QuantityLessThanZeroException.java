package com.paulorobertomartins.cleanarch.core.usecases.exceptions;

public class QuantityLessThanZeroException extends UseCaseException {

    public QuantityLessThanZeroException() {
        super("Quantity must be greater than zero.");
    }
}
