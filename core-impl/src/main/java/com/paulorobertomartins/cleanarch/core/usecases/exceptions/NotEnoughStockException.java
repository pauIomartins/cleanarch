package com.paulorobertomartins.cleanarch.core.usecases.exceptions;

public class NotEnoughStockException extends UseCaseException {

    public NotEnoughStockException() {
        super("There is no enough stock.");
    }
}
