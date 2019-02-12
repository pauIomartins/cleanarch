package com.paulorobertomartins.cleanarch.core.usecases.impl;

import com.paulorobertomartins.cleanarch.core.entities.Address;
import com.paulorobertomartins.cleanarch.core.entities.Product;
import com.paulorobertomartins.cleanarch.core.entities.Stock;
import com.paulorobertomartins.cleanarch.core.usecases.GetStockByAddress;
import com.paulorobertomartins.cleanarch.core.usecases.requestmodel.GetStockByAddressRequest;
import com.paulorobertomartins.cleanarch.core.usecases.responsemodel.GetStockResponse;
import com.paulorobertomartins.cleanarch.gateways.AddressGateway;
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
public class GetStockByAddressImplTest {

    @Mock
    private StockGateway stockGateway;

    @Mock
    private AddressGateway addressGateway;

    @Test
    public void should_get_stock_by_address() {

        final String addressLabel = "add-010";

        final Address address = new Address(addressLabel);
        Mockito.when(addressGateway.findByLabel(addressLabel)).thenReturn(Optional.of(address));

        final Product p1 = new Product(99L, "Product 099", "pro-099");
        final Product p2 = new Product(199L, "Product 199", "pro-199");
        final Product p3 = new Product(1099L, "Product 1099", "pro-1099");

        final List<Stock> stockList = new ArrayList<>();

        stockList.add(new Stock(1L, address, p1, BigDecimal.ONE));
        stockList.add(new Stock(2L, address, p2, BigDecimal.TEN));
        stockList.add(new Stock(3L, address, p3, BigDecimal.ONE));

        Mockito.when(stockGateway.findByAddress(address)).thenReturn(stockList);

        final GetStockByAddress getStockByAddress = new GetStockByAddressImpl(addressGateway, stockGateway);

        final Consumer<List<GetStockResponse>> consumer = (r) -> {
            Assert.assertEquals(stockList.size(), r.size());
            r.forEach(resp -> Assert.assertTrue(stockList.stream().anyMatch(stock -> stock.getId().equals(resp.getId()))));
        };

        getStockByAddress.execute(GetStockByAddressRequest.builder()
                        .addressLabel(addressLabel)
                        .build(),
                consumer);
    }
}