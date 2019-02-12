package com.paulorobertomartins.cleanarch.gateways;

import com.paulorobertomartins.cleanarch.core.entities.Address;
import com.paulorobertomartins.cleanarch.core.entities.Product;
import com.paulorobertomartins.cleanarch.core.entities.Stock;

import java.util.List;
import java.util.Optional;

public interface StockGateway {

    Stock create(final Stock stock);

    Stock update(final Stock stock);

    Stock createOrUpdate(final Stock stock);

    List<Stock> findAll();

    List<Stock> findByAddress(final Address address);

    List<Stock> findByProduct(final Product product);

    Optional<Stock> findByAddressAndProduct(final Address address, final Product product);

    void remove(final Stock stock);
}
