package com.paulorobertomartins.cleanarch.infra.web.controller.jsonresponse;

import com.paulorobertomartins.cleanarch.core.usecases.responsemodel.CreateAddressResponse;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
@Value
public class CreateAddressJsonResponse {

    private final Long id;
    private final String label;

    public CreateAddressJsonResponse(final CreateAddressResponse response) {
        this(response.getId(), response.getLabel());
    }
}
