package com.paulorobertomartins.cleanarch.usecases.core.usecases.impl;

import com.paulorobertomartins.cleanarch.core.entities.Address;
import com.paulorobertomartins.cleanarch.core.entities.Movement;
import com.paulorobertomartins.cleanarch.core.entities.Product;
import com.paulorobertomartins.cleanarch.core.entities.Stock;
import com.paulorobertomartins.cleanarch.core.usecases.InputStock;
import com.paulorobertomartins.cleanarch.core.usecases.exceptions.InvalidAddressException;
import com.paulorobertomartins.cleanarch.core.usecases.exceptions.InvalidProductException;
import com.paulorobertomartins.cleanarch.core.usecases.impl.InputStockImpl;
import com.paulorobertomartins.cleanarch.core.usecases.requestmodel.InputStockRequest;
import com.paulorobertomartins.cleanarch.core.usecases.responsemodel.InputStockResponse;
import com.paulorobertomartins.cleanarch.gateways.AddressGateway;
import com.paulorobertomartins.cleanarch.gateways.MovementGateway;
import com.paulorobertomartins.cleanarch.gateways.ProductGateway;
import com.paulorobertomartins.cleanarch.gateways.StockGateway;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class InputStockImplTest {

    @Mock
    private AddressGateway addressGateway;
    @Mock
    private ProductGateway productGateway;
    @Mock
    private StockGateway stockGateway;
    @Mock
    private MovementGateway movementGateway;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void should_include_product_on_address_when_stock_is_empty() {

        final Double inputQuantity = Math.random();
        final String addressLabel = "address-001";
        final String productEan = "product-001";

        final Address address = new Address(123L, addressLabel);
        Mockito.when(addressGateway.findByLabel(addressLabel)).thenReturn(Optional.of(address));

        final Product product = new Product(999L, "Test Product", productEan);
        Mockito.when(productGateway.findByEan(productEan)).thenReturn(Optional.of(product));

        final Stock stock = new Stock(234L, address, product, inputQuantity);
        Mockito.when(stockGateway.findByAddressAndProduct(address, product)).thenReturn(Optional.empty());
        Mockito.when(stockGateway.create(new Stock(null, address, product, inputQuantity))).thenReturn(stock);

        final Movement movement = new Movement(address, product, inputQuantity, Movement.MovementType.INPUT);
        movement.setId(8768678L);
        Mockito.when(movementGateway.create(new Movement(address, product, inputQuantity, Movement.MovementType.INPUT)))
                .thenReturn(movement);

        final InputStock inputStock = new InputStockImpl(stockGateway, addressGateway, productGateway, movementGateway);

        final InputStockRequest request = InputStockRequest.builder()
                .addressLabel(addressLabel)
                .productEan(productEan)
                .quantity(inputQuantity).build();

        final Consumer<InputStockResponse> consumer = (response) -> {
            assertEquals(response.getAddressLabel(), addressLabel);
            assertEquals(response.getProductEan(), productEan);
            assertEquals(response.getQuantity(), inputQuantity);
            assertEquals(response.getStockId(), stock.getId());
        };

        inputStock.execute(request, consumer);
    }

    @Test
    public void should_increment_stock_when_input_quantity_and_already_has_stock() {

        final Double inputQuantity = Math.random();
        final String addressLabel = "address-001";
        final String productEan = "product-001";

        final Address address = new Address(123L, addressLabel);
        Mockito.when(addressGateway.findByLabel(addressLabel)).thenReturn(Optional.of(address));

        final Product product = new Product(999L, "Test Product", productEan);
        Mockito.when(productGateway.findByEan(productEan)).thenReturn(Optional.of(product));

        final Stock stock = new Stock(234L, address, product, inputQuantity);
        Mockito.when(stockGateway.findByAddressAndProduct(address, product)).thenReturn(Optional.of(stock));

        final Double updatedQuantity = inputQuantity + inputQuantity;
        final Stock updatedStock = new Stock(234L, address, product, updatedQuantity);
        Mockito.when(stockGateway.update(updatedStock)).thenReturn(updatedStock);

        final Movement movement = new Movement(address, product, inputQuantity, Movement.MovementType.INPUT);
        movement.setId(8768678L);
        Mockito.when(movementGateway.create(new Movement(address, product, inputQuantity, Movement.MovementType.INPUT)))
                .thenReturn(movement);

        final InputStock inputStock = new InputStockImpl(stockGateway, addressGateway, productGateway, movementGateway);

        final InputStockRequest request = InputStockRequest.builder()
                .addressLabel(addressLabel)
                .productEan(productEan)
                .quantity(inputQuantity).build();

        final Consumer<InputStockResponse> consumer = (response) -> {
            assertEquals(response.getAddressLabel(), addressLabel);
            assertEquals(response.getProductEan(), productEan);
            assertEquals(response.getQuantity(), updatedQuantity);
            assertEquals(response.getStockId(), stock.getId());
        };

        inputStock.execute(request, consumer);
    }

    @Test(expected = InvalidAddressException.class)
    public void must_throw_exception_with_invalid_address() {

        final Double inputQuantity = Math.random();
        final String addressLabel = "address-001";
        final String productEan = "product-001";

        Mockito.when(addressGateway.findByLabel(addressLabel)).thenReturn(Optional.empty());

        final InputStock inputStock = new InputStockImpl(stockGateway, addressGateway, productGateway, movementGateway);

        final InputStockRequest request = InputStockRequest.builder()
                .addressLabel(addressLabel)
                .productEan(productEan)
                .quantity(inputQuantity).build();

        inputStock.execute(request, (r) -> {
        });
    }

    @Test(expected = InvalidProductException.class)
    public void must_throw_exception_with_invalid_product() {

        final Double inputQuantity = Math.random();
        final String addressLabel = "address-001";
        final String productEan = "product-001";

        final Address address = new Address(123L, addressLabel);
        Mockito.when(addressGateway.findByLabel(addressLabel)).thenReturn(Optional.of(address));

        Mockito.when(productGateway.findByEan(productEan)).thenReturn(Optional.empty());

        final InputStock inputStock = new InputStockImpl(stockGateway, addressGateway, productGateway, movementGateway);

        final InputStockRequest request = InputStockRequest.builder()
                .addressLabel(addressLabel)
                .productEan(productEan)
                .quantity(inputQuantity).build();

        inputStock.execute(request, (r) -> {
        });
    }
}