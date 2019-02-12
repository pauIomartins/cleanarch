package com.paulorobertomartins.cleanarch.core.usecases.impl;

import com.paulorobertomartins.cleanarch.core.entities.Address;
import com.paulorobertomartins.cleanarch.core.entities.Product;
import com.paulorobertomartins.cleanarch.core.entities.Stock;
import com.paulorobertomartins.cleanarch.core.usecases.CreateOrUpdateStock;
import com.paulorobertomartins.cleanarch.gateways.StockGateway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

@RunWith(MockitoJUnitRunner.class)
public class CreateOrUpdateStockImplTest {

    @Mock
    private StockGateway stockGateway;

    @Test
    public void should_create_with_create_or_update() {

        final Address address = new Address(1l, "test-address");
        final Product product = new Product(99L, "Test Product", "prod-01");

        Mockito.when(stockGateway.findByAddressAndProduct(address, product)).thenReturn(Optional.empty());

        final CreateOrUpdateStock createOrUpdateStock = new CreateOrUpdateStockImpl(stockGateway);

        final Stock stock = createOrUpdateStock.execute(address, product, BigDecimal.TEN);

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

        Mockito.when(stockGateway.findByAddressAndProduct(address, product)).thenReturn(Optional.of(oldStock));

        final CreateOrUpdateStock createOrUpdateStock = new CreateOrUpdateStockImpl(stockGateway);

        final Stock newStock = createOrUpdateStock.execute(address, product, BigDecimal.TEN);

        assertNotNull(newStock);
        assertSame(oldStock, newStock);
        assertEquals(newStock.getId(), stockId);
        assertEquals(newStock.getAddress(), address);
        assertEquals(newStock.getProduct(), product);
        assertEquals(newStock.getQuantity(), BigDecimal.TEN);
    }
}