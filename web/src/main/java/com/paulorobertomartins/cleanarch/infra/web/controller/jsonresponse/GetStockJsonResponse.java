package com.paulorobertomartins.cleanarch.infra.web.controller.jsonresponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.paulorobertomartins.cleanarch.core.usecases.responsemodel.GetStockResponse;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Value
public class GetStockJsonResponse {

    @JsonProperty("stock_id")
    private final Long id;
    @JsonProperty("address_label")
    private final String addressLabel;
    @JsonProperty("product_ean")
    private final String productEan;
    @JsonProperty("quantity")
    private final BigDecimal quantity;

    public GetStockJsonResponse(final GetStockResponse response) {
        this(response.getId(), response.getAddressLabel(), response.getProductEan(), response.getQuantity());
    }
}
