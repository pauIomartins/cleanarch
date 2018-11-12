package com.paulorobertomartins.cleanarch.gateways;

import com.paulorobertomartins.cleanarch.core.entities.Product;

import java.util.Optional;

public interface ProductGateway extends EntityGateway<Product> {

    Optional<Product> findById(final long productId);

    Optional<Product> findByEan(final String productEan);
}
