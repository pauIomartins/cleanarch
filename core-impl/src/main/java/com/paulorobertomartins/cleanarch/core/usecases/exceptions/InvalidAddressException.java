package com.paulorobertomartins.cleanarch.core.usecases.exceptions;

public class InvalidAddressException extends UseCaseException {

    public InvalidAddressException() {
        super("Invalid address.");
    }
}
