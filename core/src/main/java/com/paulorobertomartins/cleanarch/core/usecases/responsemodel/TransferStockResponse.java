package com.paulorobertomartins.cleanarch.core.usecases.responsemodel;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Builder(toBuilder = true)
@Value
public class TransferStockResponse {

    private final Long movementId;
    private final String addressFromLabel;
    private final String addressToLabel;
    private final String productEan;
    private final BigDecimal quantity;
    private final Long stockId;
}
