package com.paulorobertomartins.cleanarch.core.usecases.impl;

import com.paulorobertomartins.cleanarch.core.entities.Address;
import com.paulorobertomartins.cleanarch.core.entities.Product;
import com.paulorobertomartins.cleanarch.core.entities.Stock;
import com.paulorobertomartins.cleanarch.core.usecases.CreateOrUpdateStock;
import com.paulorobertomartins.cleanarch.gateways.StockGateway;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Named
public class CreateOrUpdateStockImpl implements CreateOrUpdateStock {

    private final StockGateway stockGateway;

    @Override
    public Stock execute(Address address, Product product, BigDecimal quantity) {

        final Optional<Stock> stockOptional = stockGateway.findByAddressAndProduct(address, product);

        final Stock stock;

        if (stockOptional.isPresent()) {
            stock = stockOptional.get();
            stock.incrementQuantity(quantity);
        } else {
            stock = new Stock(address, product, quantity);
        }
        return stock;
    }
}
