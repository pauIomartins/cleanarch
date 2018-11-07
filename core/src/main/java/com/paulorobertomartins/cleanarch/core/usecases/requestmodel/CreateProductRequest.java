package com.paulorobertomartins.cleanarch.core.usecases.requestmodel;

import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class CreateProductRequest {

    private final String description;
    private final String ean;
}
