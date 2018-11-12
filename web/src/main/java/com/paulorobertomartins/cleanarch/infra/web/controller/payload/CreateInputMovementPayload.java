package com.paulorobertomartins.cleanarch.infra.web.controller.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.paulorobertomartins.cleanarch.core.usecases.requestmodel.InputStockRequest;

public class CreateInputMovementPayload extends CreateMovementPayload<InputStockRequest> {

    public CreateInputMovementPayload(@JsonProperty("address_to") String addressToLabel,
                                      @JsonProperty("product_ean") String productEan,
                                      @JsonProperty("quantity") Double quantity) {
        super(null, addressToLabel, productEan, quantity);
    }

    @Override
    public InputStockRequest toRequest() {
        return InputStockRequest.builder()
                .addressLabel(this.getAddressToLabel())
                .productEan(this.getProductEan())
                .quantity(this.getQuantity())
                .build();
    }
}
