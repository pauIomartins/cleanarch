package com.paulorobertomartins.cleanarch.core.usecases.impl;

import com.paulorobertomartins.cleanarch.core.entities.Address;
import com.paulorobertomartins.cleanarch.core.entities.Product;
import com.paulorobertomartins.cleanarch.core.entities.Stock;
import com.paulorobertomartins.cleanarch.core.usecases.GetAllStock;
import com.paulorobertomartins.cleanarch.core.usecases.responsemodel.GetStockResponse;
import com.paulorobertomartins.cleanarch.gateways.StockGateway;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@RunWith(MockitoJUnitRunner.class)
public class GetAllStockImplTest {

    @Mock
    private StockGateway stockGateway;

    @Test
    public void should_get_all_stock() {

        final List<Stock> stockList = new ArrayList<>();

        stockList.add(new Stock(1L, new Address("add-001"), new Product("Product 001", "pro-001"), BigDecimal.ONE));
        stockList.add(new Stock(2L, new Address("add-002"), new Product("Product 001", "pro-001"), BigDecimal.ONE));
        stockList.add(new Stock(3L, new Address("add-003"), new Product("Product 001", "pro-001"), BigDecimal.ONE));
        stockList.add(new Stock(4L, new Address("add-001"), new Product("Product 002", "pro-002"), BigDecimal.ONE));
        stockList.add(new Stock(5L, new Address("add-002"), new Product("Product 002", "pro-002"), BigDecimal.ONE));

        Mockito.when(stockGateway.findAll()).thenReturn(stockList);

        final GetAllStock getAllStock = new GetAllStockImpl(stockGateway);

        final Consumer<List<GetStockResponse>> consumer = (r) -> {
            Assert.assertEquals(stockList.size(), r.size());
            r.forEach(resp -> Assert.assertTrue(stockList.stream().anyMatch(stock -> stock.getId().equals(resp.getId()))));
        };

        getAllStock.execute(null, consumer);
    }

    @Test
    public void should_get_empty_stock() {

        Mockito.when(stockGateway.findAll()).thenReturn(new ArrayList<>());

        final GetAllStock getAllStock = new GetAllStockImpl(stockGateway);

        final Consumer<List<GetStockResponse>> consumer = (r) -> {
            Assert.assertTrue(r.isEmpty());
        };

        getAllStock.execute(null, consumer);
    }

}