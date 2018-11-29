package com.paulorobertomartins.cleanarch.core.usecases.exceptions;

public class AddresLabelEmptyException extends UseCaseException {

    public AddresLabelEmptyException() {
        super("Address label cannot be empty.");
    }
}
