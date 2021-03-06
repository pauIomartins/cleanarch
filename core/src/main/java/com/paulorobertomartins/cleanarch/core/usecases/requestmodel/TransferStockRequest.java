package com.paulorobertomartins.cleanarch.core.usecases.requestmodel;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Builder(toBuilder = true)
@Value
public class TransferStockRequest {

    private final String addressFromLabel;
    private final String addressToLabel;
    private final String productEan;
    private final BigDecimal quantity;
}
