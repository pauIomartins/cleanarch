package com.paulorobertomartins.cleanarch.core.usecases;

import com.paulorobertomartins.cleanarch.core.entities.Address;
import com.paulorobertomartins.cleanarch.core.entities.Product;
import com.paulorobertomartins.cleanarch.core.entities.Stock;

import java.math.BigDecimal;

@FunctionalInterface
public interface CreateOrUpdateStock {

    Stock execute(final Address address, final Product product, final BigDecimal quantity);
}
