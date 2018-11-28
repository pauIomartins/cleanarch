package com.paulorobertomartins.cleanarch.infra.web.controller.jsonresponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Builder(toBuilder = true)
@Value
public class CreateMovementJsonResponse {

    @JsonProperty("stock_id")
    private Long stockId;

    @JsonProperty("movement_id")
    private Long movementId;

    @JsonProperty("address_from")
    private String addressFrom;

    @JsonProperty("address_to")
    private String addressTo;

    @JsonProperty("product_ean")
    private String productEan;

    @JsonProperty("quantity")
    private BigDecimal quantity;

    @JsonProperty("type")
    private String type;
}
