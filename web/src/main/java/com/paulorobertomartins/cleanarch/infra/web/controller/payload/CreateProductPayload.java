package com.paulorobertomartins.cleanarch.infra.web.controller.payload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.paulorobertomartins.cleanarch.core.usecases.requestmodel.CreateProductRequest;
import lombok.Value;

@Value
public class CreateProductPayload {

    private final String ean;
    private final String description;

    @JsonCreator
    public CreateProductPayload(@JsonProperty("ean") String ean, @JsonProperty("description") String description) {
        this.ean = ean;
        this.description = description;
    }

    public CreateProductRequest toCreateProductRequest() {
        return CreateProductRequest.builder()
                .description(description)
                .ean(ean)
                .build();
    }
}
