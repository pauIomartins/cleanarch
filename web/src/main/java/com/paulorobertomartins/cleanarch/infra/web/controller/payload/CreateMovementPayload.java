package com.paulorobertomartins.cleanarch.infra.web.controller.payload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreateInputMovementPayload.class, name = "input")
})
@Getter
public abstract class CreateMovementPayload<T> {

    private final String addressFromLabel;
    private final String addressToLabel;
    private final String productEan;
    private final Double quantity;

    @JsonCreator
    public CreateMovementPayload(@JsonProperty("address_from") String addressFromLabel,
                                 @JsonProperty("address_to") String addressToLabel,
                                 @JsonProperty("product_ean") String productEan,
                                 @JsonProperty("quantity") Double quantity) {
        this.addressFromLabel = addressFromLabel;
        this.addressToLabel = addressToLabel;
        this.productEan = productEan;
        this.quantity = quantity;
    }

    public abstract T toRequest();
}
