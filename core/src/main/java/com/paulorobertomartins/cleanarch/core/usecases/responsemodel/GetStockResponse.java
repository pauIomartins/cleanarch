package com.paulorobertomartins.cleanarch.core.usecases.responsemodel;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Builder(toBuilder = true)
@Value
public class GetStockResponse {

    private final Long id;
    private final String addressLabel;
    private final String productEan;
    private final BigDecimal quantity;
}
