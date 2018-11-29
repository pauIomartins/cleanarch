package com.paulorobertomartins.cleanarch.core.usecases.exceptions;

public class InvalidProductException extends UseCaseException {

    public InvalidProductException() {
        super("Invalid product.");
    }
}
