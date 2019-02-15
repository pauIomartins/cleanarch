package com.paulorobertomartins.cleanarch.core.usecases.exceptions;

public class AddressLabelEmptyException extends UseCaseException {

    public AddressLabelEmptyException() {
        super("Address label cannot be empty.");
    }
}
