package com.paulorobertomartins.cleanarch.core.usecases.exceptions;

public class StockNotExistsException extends UseCaseException {

    public StockNotExistsException() {
        super("There is no stock available.");
    }
}
