package com.paulorobertomartins.cleanarch.infra.web.controller.payload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.paulorobertomartins.cleanarch.core.usecases.requestmodel.CreateAddressRequest;
import lombok.Value;

@Value
public class CreateAddressPayload {

    private final String addressLabel;

    @JsonCreator
    public CreateAddressPayload(@JsonProperty("address_label") String addressLabel) {
        this.addressLabel = addressLabel;
    }

    public CreateAddressRequest toCreateAddressRequest() {
        return CreateAddressRequest.builder().addressLabel(addressLabel).build();
    }
}
