package com.paulorobertomartins.cleanarch.core.usecases.responsemodel;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Builder(toBuilder = true)
@Value
public class InputStockResponse {

    private final Long stockId;
    private final Long movementId;
    private final String addressLabel;
    private final String productEan;
    private final BigDecimal quantity;
}
