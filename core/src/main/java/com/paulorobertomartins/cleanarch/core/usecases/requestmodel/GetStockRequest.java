package com.paulorobertomartins.cleanarch.core.usecases.requestmodel;

import lombok.Builder;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class GetStockRequest {

    private final String addressLabel;
}
