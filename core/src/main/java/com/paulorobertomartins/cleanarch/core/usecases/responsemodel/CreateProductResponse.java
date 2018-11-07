package com.paulorobertomartins.cleanarch.core.usecases.responsemodel;

import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class CreateProductResponse {

    private final Long id;
    private final String description;
    private final String ean;
}
