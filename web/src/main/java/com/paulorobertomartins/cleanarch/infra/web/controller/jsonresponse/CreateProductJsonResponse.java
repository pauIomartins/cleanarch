package com.paulorobertomartins.cleanarch.infra.web.controller.jsonresponse;

import com.paulorobertomartins.cleanarch.core.usecases.responsemodel.CreateProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
@Value
public class CreateProductJsonResponse {

    private final Long id;
    private final String description;
    private final String ean;

    public CreateProductJsonResponse(final CreateProductResponse response) {
        this(response.getId(), response.getDescription(), response.getEan());
    }
}
