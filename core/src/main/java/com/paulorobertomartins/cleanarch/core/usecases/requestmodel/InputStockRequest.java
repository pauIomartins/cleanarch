package com.paulorobertomartins.cleanarch.core.usecases.requestmodel;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Builder(toBuilder = true)
@Value
public class InputStockRequest {

    private final String addressLabel;
    private final String productEan;
    private final BigDecimal quantity;
}
