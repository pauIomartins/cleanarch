package com.paulorobertomartins.cleanarch.core.usecases.responsemodel;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder(toBuilder = true)
@Value
public class GetStockResponse {

    private List<StockResponse> stock;

    //@Builder(toBuilder = true)
    @Value
    public class StockResponse {
        private final Long id;
        private final String addressLabel;
        private final String productEan;
        private final Double quantity;
    }
}
