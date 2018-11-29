package com.paulorobertomartins.cleanarch.core.usecases.exceptions;

public class ProductEanEmptyException extends UseCaseException {

    public ProductEanEmptyException() {
        super("Product EAN cannot be empty.");
    }
}
