package com.paulorobertomartins.cleanarch.infra.web.controller.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.paulorobertomartins.cleanarch.core.usecases.requestmodel.TransferStockRequest;

import java.math.BigDecimal;

public class ExecuteTransferMovementPayload extends ExecuteMovementPayload<TransferStockRequest> {

    public ExecuteTransferMovementPayload(@JsonProperty("address_from") String addressFromLabel,
                                          @JsonProperty("address_to") String addressToLabel,
                                          @JsonProperty("product_ean") String productEan,
                                          @JsonProperty("quantity") BigDecimal quantity) {
        super(addressFromLabel, addressToLabel, productEan, quantity);
    }

    @Override
    public TransferStockRequest toRequest() {
        return TransferStockRequest.builder()
                .addressFromLabel(this.getAddressFromLabel())
                .addressToLabel(this.getAddressToLabel())
                .productEan(this.getProductEan())
                .quantity(this.getQuantity())
                .build();
    }
}
