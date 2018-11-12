package com.paulorobertomartins.cleanarch.core.usecases.responsemodel;

import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class InputStockResponse {

    private final Long stockId;
    private final String addressLabel;
    private final String productEan;
    private final Double quantity;
}
