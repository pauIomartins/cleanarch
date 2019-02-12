package com.paulorobertomartins.cleanarch.core.usecases.impl;

import com.paulorobertomartins.cleanarch.core.usecases.GetAllStock;
import com.paulorobertomartins.cleanarch.core.usecases.responsemodel.GetStockResponse;
import com.paulorobertomartins.cleanarch.gateways.StockGateway;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Named
@Transactional
@RequiredArgsConstructor
public class GetAllStockImpl implements GetAllStock {

    private final StockGateway stockGateway;

    @Override
    public void execute(Void request, Consumer<List<GetStockResponse>> consumer) {
        consumer.accept(stockGateway.findAll().stream().map(s ->
                GetStockResponse.builder()
                        .id(s.getId())
                        .addressLabel(s.getAddress().getLabel())
                        .productEan(s.getProduct().getEan())
                        .quantity(s.getQuantity())
                        .build()
        ).collect(Collectors.toList()));
    }
}
