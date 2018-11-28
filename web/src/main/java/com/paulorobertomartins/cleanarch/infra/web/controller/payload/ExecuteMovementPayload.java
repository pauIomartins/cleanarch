package com.paulorobertomartins.cleanarch.infra.web.controller.payload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;

import java.math.BigDecimal;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ExecuteInputMovementPayload.class, name = "input"),
        @JsonSubTypes.Type(value = ExecuteTransferMovementPayload.class, name = "transfer")
})
@Getter
public abstract class ExecuteMovementPayload<T> {

    private final String addressFromLabel;
    private final String addressToLabel;
    private final String productEan;
    private final BigDecimal quantity;

    @JsonCreator
    public ExecuteMovementPayload(@JsonProperty("address_from") String addressFromLabel,
                                  @JsonProperty("address_to") String addressToLabel,
                                  @JsonProperty("product_ean") String productEan,
                                  @JsonProperty("quantity") BigDecimal quantity) {
        this.addressFromLabel = addressFromLabel;
        this.addressToLabel = addressToLabel;
        this.productEan = productEan;
        this.quantity = quantity;
    }

    public abstract T toRequest();
}
