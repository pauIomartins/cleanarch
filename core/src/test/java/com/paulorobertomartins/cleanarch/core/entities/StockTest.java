package com.paulorobertomartins.cleanarch.core.entities;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.*;

public class StockTest {

    @Test
    public void should_increment_quantity() {

        final Address address = new Address(1l, "test-address");
        final Product product = new Product(99L, "Test Product", "prod-01");

        final BigDecimal initial = new BigDecimal(Math.random());
        final BigDecimal increment = new BigDecimal(Math.random());
        final Stock stock = new Stock(address, product, initial);

        stock.incrementQuantity(increment);

        assertEquals(stock.getQuantity(), initial.add(increment));
    }

    @Test
    public void should_decrement_quantity() {

        final Address address = new Address(1l, "test-address");
        final Product product = new Product(99L, "Test Product", "prod-01");

        final BigDecimal initial = new BigDecimal(Math.random());
        final BigDecimal decrement = new BigDecimal(Math.random());
        final Stock stock = new Stock(address, product, initial);

        stock.decrementQuantity(decrement);

        assertEquals(stock.getQuantity(), initial.subtract(decrement));
    }

    @Test
    public void should_is_empty() {

        final Address address = new Address(1l, "test-address");
        final Product product = new Product(99L, "Test Product", "prod-01");

        final Stock stock = new Stock(address, product, BigDecimal.ZERO);

        assertTrue(stock.isEmpty());
    }

    @Test
    public void should_create_with_create_or_update() {

        final Address address = new Address(1l, "test-address");
        final Product product = new Product(99L, "Test Product", "prod-01");

        final Stock stock = Stock.createOrUpdate(Optional.empty(), address, product, BigDecimal.TEN);

        assertNotNull(stock);
        assertEquals(stock.getAddress(), address);
        assertEquals(stock.getProduct(), product);
        assertEquals(stock.getQuantity(), BigDecimal.TEN);
    }


    @Test
    public void should_update_with_create_or_update() {

        final Address address = new Address(1l, "test-address");
        final Product product = new Product(99L, "Test Product", "prod-01");

        final Long stockId = 999L;

        final Stock oldStock = new Stock(stockId, address, product, BigDecimal.ZERO);

        final Stock newStock = Stock.createOrUpdate(Optional.of(oldStock),
                address, product, BigDecimal.TEN);

        assertNotNull(newStock);
        assertSame(oldStock, newStock);
        assertEquals(newStock.getId(), stockId);
        assertEquals(newStock.getAddress(), address);
        assertEquals(newStock.getProduct(), product);
        assertEquals(newStock.getQuantity(), BigDecimal.TEN);
    }
}