package com.paulorobertomartins.cleanarch.infra.web.controller.jsonresponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class CreateMovementJsonResponse {

    @JsonProperty("stock_id")
    private Long stockId;

    @JsonProperty("address_from")
    private String addressFrom;

    @JsonProperty("address_to")
    private String addressTo;

    @JsonProperty("product_ean")
    private String productEan;

    @JsonProperty("quantity")
    private Double quantity;

    @JsonProperty("type")
    private String type;
}
