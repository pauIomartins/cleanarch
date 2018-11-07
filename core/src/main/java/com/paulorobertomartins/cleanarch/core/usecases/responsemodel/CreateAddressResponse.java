package com.paulorobertomartins.cleanarch.core.usecases.responsemodel;

import lombok.*;

@Builder(toBuilder = true)
@Value
public class CreateAddressResponse {

    private final Long id;
    private final String label;
}