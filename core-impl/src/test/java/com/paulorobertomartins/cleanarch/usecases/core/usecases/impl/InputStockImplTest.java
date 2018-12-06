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
public class InputStockImplTest {

    @Mock
    private AddressGateway addressGateway;
    @Mock
    private ProductGateway productGateway;
    @Mock
    private StockGateway stockGateway;
    @Mock
    private MovementGateway movementGateway;

    @Test
    public void should_include_product_on_address_when_stock_is_empty() {

        final BigDecimal inputQuantity = BigDecimal.valueOf(Math.random());
        final String addressLabel = "address-001";
        final String productEan = "product-001";

        final Address address = new Address(123L, addressLabel);
        Mockito.when(addressGateway.findByLabel(addressLabel)).thenReturn(Optional.of(address));

        final Product product = new Product(999L, "Test Product", productEan);
        Mockito.when(productGateway.findByEan(productEan)).thenReturn(Optional.of(product));

        final Stock stock = new Stock(234L, address, product, inputQuantity);
        Mockito.when(stockGateway.findByAddressAndProduct(address, product)).thenReturn(Optional.empty());
        Mockito.when(stockGateway.createOrUpdate(new Stock(null, address, product, inputQuantity))).thenReturn(stock);

        final Movement movement = Movement.builder()
                .id(8768678L)
                .addressTo(address)
                .product(product)
                .quantity(inputQuantity)
                .type(Movement.MovementType.INPUT)
                .build();

        Mockito.when(movementGateway.create(new Movement(address, product, inputQuantity, Movement.MovementType.INPUT)))
                .thenReturn(movement);

        final InputStock inputStock = new InputStockImpl(stockGateway, addressGateway, productGateway, movementGateway);

        final InputStockRequest request = InputStockRequest.builder()
                .addressLabel(addressLabel)
                .productEan(productEan)
                .quantity(inputQuantity).build();

        inputStock.execute(request, assertResponse(addressLabel, productEan, inputQuantity, stock.getId(), movement.getId()));
    }

    @Test
    public void should_increment_stock_when_input_quantity_and_already_has_stock() {

        final BigDecimal inputQuantity = BigDecimal.valueOf(Math.random());
        final String addressLabel = "address-001";
        final String productEan = "product-001";

        final Address address = new Address(123L, addressLabel);
        Mockito.when(addressGateway.findByLabel(addressLabel)).thenReturn(Optional.of(address));

        final Product product = new Product(999L, "Test Product", productEan);
        Mockito.when(productGateway.findByEan(productEan)).thenReturn(Optional.of(product));

        final Stock stock = new Stock(234L, address, product, inputQuantity);
        Mockito.when(stockGateway.findByAddressAndProduct(address, product)).thenReturn(Optional.of(stock));

        final BigDecimal updatedQuantity = inputQuantity.add(inputQuantity);
        final Stock updatedStock = new Stock(234L, address, product, updatedQuantity);
        Mockito.when(stockGateway.createOrUpdate(updatedStock)).thenReturn(updatedStock);

        final Movement movement = new Movement(8768678L, null, address, product, inputQuantity, Movement.MovementType.INPUT);
        Mockito.when(movementGateway.create(new Movement(address, product, inputQuantity, Movement.MovementType.INPUT)))
                .thenReturn(movement);

        final InputStock inputStock = new InputStockImpl(stockGateway, addressGateway, productGateway, movementGateway);

        final InputStockRequest request = InputStockRequest.builder()
                .addressLabel(addressLabel)
                .productEan(productEan)
                .quantity(inputQuantity).build();

        inputStock.execute(request, assertResponse(addressLabel, productEan, inputQuantity, stock.getId(), movement.getId()));
    }

    @Test(expected = InvalidAddressException.class)
    public void must_throw_exception_with_invalid_address() {

        final BigDecimal inputQuantity = BigDecimal.valueOf(Math.random());
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

        final BigDecimal inputQuantity = BigDecimal.valueOf(Math.random());
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

    private Consumer<InputStockResponse> assertResponse(final String addressLabel, final String productEan, final BigDecimal quantity, final Long stockId, final Long movementId) {
        return (internalResponse) -> {
            assertEquals(internalResponse.getAddressLabel(), addressLabel);
            assertEquals(internalResponse.getProductEan(), productEan);
            assertEquals(internalResponse.getQuantity(), quantity);
            assertEquals(internalResponse.getStockId(), stockId);
            assertEquals(internalResponse.getMovementId(), movementId);
        };
    }
}