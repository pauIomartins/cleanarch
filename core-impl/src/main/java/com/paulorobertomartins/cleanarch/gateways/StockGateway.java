package com.paulorobertomartins.cleanarch.gateways;

import com.paulorobertomartins.cleanarch.core.entities.Address;
import com.paulorobertomartins.cleanarch.core.entities.Product;
import com.paulorobertomartins.cleanarch.core.entities.Stock;

import java.util.Optional;

public interface StockGateway {

    Stock create(final Stock stock);

    Stock update(final Stock stock);

    Stock createOrUpdate(final Stock stock);

    Optional<Stock> findByAddressAndProduct(final Address address, final Product product);

    void remove(final Stock stock);
}
