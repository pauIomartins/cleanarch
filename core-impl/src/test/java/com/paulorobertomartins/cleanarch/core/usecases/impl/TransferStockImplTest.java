package com.paulorobertomartins.cleanarch.core.usecases.impl;

import com.paulorobertomartins.cleanarch.core.entities.Address;
import com.paulorobertomartins.cleanarch.core.entities.Movement;
import com.paulorobertomartins.cleanarch.core.entities.Product;
import com.paulorobertomartins.cleanarch.core.entities.Stock;
import com.paulorobertomartins.cleanarch.core.usecases.CreateOrUpdateStock;
import com.paulorobertomartins.cleanarch.core.usecases.requestmodel.TransferStockRequest;
import com.paulorobertomartins.cleanarch.core.usecases.responsemodel.TransferStockResponse;
import com.paulorobertomartins.cleanarch.gateways.AddressGateway;
import com.paulorobertomartins.cleanarch.gateways.MovementGateway;
import com.paulorobertomartins.cleanarch.gateways.ProductGateway;
import com.paulorobertomartins.cleanarch.gateways.StockGateway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TransferStockImplTest {

    @Mock
    private StockGateway stockGateway;
    @Mock
    private AddressGateway addressGateway;
    @Mock
    private ProductGateway productGateway;
    @Mock
    private MovementGateway movementGateway;
    @Mock
    private CreateOrUpdateStock createOrUpdateStock;

    @Test
    public void should_transfer_stock() {

        final String addressFromLabel = "add-001";
        final Address addressFrom = new Address(1L, addressFromLabel);
        Mockito.when(addressGateway.findByLabel(addressFromLabel)).thenReturn(Optional.of(addressFrom));

        final String addressToLabel = "add-002";
        final Address addressTo = new Address(2L, addressToLabel);
        Mockito.when(addressGateway.findByLabel(addressToLabel)).thenReturn(Optional.of(addressTo));

        final String productEan = "prod-001";
        final Product product = new Product(99L, "prod 001", productEan);
        Mockito.when(productGateway.findByEan(productEan)).thenReturn(Optional.of(product));

        final Stock stock = new Stock(777L, addressFrom, product, BigDecimal.valueOf(100.0));
        Mockito.when(stockGateway.findByAddressAndProduct(addressFrom, product)).thenReturn(Optional.of(stock));

        final BigDecimal quantity = BigDecimal.valueOf(12.0);

        final Stock newStockOnAddressTo = new Stock(888L, addressTo, product, quantity);
        Mockito.when(createOrUpdateStock.execute(addressTo, product, quantity)).thenReturn(newStockOnAddressTo);
        Mockito.when(stockGateway.createOrUpdate(newStockOnAddressTo)).thenReturn(newStockOnAddressTo);

        final Movement movement = new Movement(999L, addressFrom, addressTo, product, quantity, Movement.MovementType.TRANSFER);
        Mockito.when(movementGateway.create(new Movement(addressFrom, addressTo, product, quantity, Movement.MovementType.TRANSFER)))
                .thenReturn(movement);

        final TransferStockImpl transferStock = new TransferStockImpl(stockGateway, addressGateway, productGateway, movementGateway, createOrUpdateStock);

        final TransferStockRequest request = TransferStockRequest.builder()
                .addressFromLabel(addressFromLabel)
                .addressToLabel(addressToLabel)
                .productEan(productEan)
                .quantity(quantity)
                .build();

        Consumer<TransferStockResponse> consumer = (r) -> {
            assertEquals(r.getAddressFromLabel(), addressFromLabel);
            assertEquals(r.getAddressToLabel(), addressToLabel);
            assertEquals(r.getProductEan(), productEan);
            assertEquals(r.getQuantity(), quantity);
            assertEquals((long) r.getStockId(), 888L);
            assertEquals((long) r.getMovementId(), 999L);
        };

        transferStock.execute(request, consumer);
    }

}