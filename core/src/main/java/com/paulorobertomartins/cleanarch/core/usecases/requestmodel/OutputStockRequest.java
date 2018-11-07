package com.paulorobertomartins.cleanarch.core.usecases.requestmodel;

import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class OutputStockRequest {

    private final String addressLabel;
    private final String productEan;
    private final Double quantity;
}
