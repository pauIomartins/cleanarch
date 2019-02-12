package com.paulorobertomartins.cleanarch.core.usecases.impl;

import com.paulorobertomartins.cleanarch.core.entities.Address;
import com.paulorobertomartins.cleanarch.core.entities.Product;
import com.paulorobertomartins.cleanarch.core.entities.Stock;
import com.paulorobertomartins.cleanarch.core.usecases.GetStockByProduct;
import com.paulorobertomartins.cleanarch.core.usecases.requestmodel.GetStockByProductRequest;
import com.paulorobertomartins.cleanarch.core.usecases.responsemodel.GetStockResponse;
import com.paulorobertomartins.cleanarch.gateways.ProductGateway;
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
import java.util.Optional;
import java.util.function.Consumer;

@RunWith(MockitoJUnitRunner.class)
public class GetStockByProductImplTest {

    @Mock
    private StockGateway stockGateway;

    @Mock
    private ProductGateway productGateway;

    @Test
    public void should_get_stock_by_product() {

        final String productEan = "product-123";

        final Product product = new Product("Product 123", productEan);
        Mockito.when(productGateway.findByEan(productEan)).thenReturn(Optional.of(product));

        final Address a1 = new Address("add-001");
        final Address a2 = new Address("add-002");
        final Address a3 = new Address("add-003");
        final Address a4 = new Address("add-004");
        final Address a5 = new Address("add-005");

        final List<Stock> stockList = new ArrayList<>();

        stockList.add(new Stock(1L, a1, product, BigDecimal.ONE));
        stockList.add(new Stock(2L, a2, product, BigDecimal.TEN));
        stockList.add(new Stock(3L, a3, product, BigDecimal.ONE));
        stockList.add(new Stock(4L, a4, product, BigDecimal.TEN));
        stockList.add(new Stock(5L, a5, product, BigDecimal.ONE));

        Mockito.when(stockGateway.findByProduct(product)).thenReturn(stockList);

        final GetStockByProduct getStockByProduct = new GetStockByProductImpl(productGateway, stockGateway);

        final Consumer<List<GetStockResponse>> consumer = (r) -> {
            Assert.assertEquals(stockList.size(), r.size());
            r.forEach(resp -> Assert.assertTrue(stockList.stream().anyMatch(stock -> stock.getId().equals(resp.getId()))));
        };

        getStockByProduct.execute(GetStockByProductRequest.builder()
                        .productEan(productEan)
                        .build(),
                consumer);
    }
}