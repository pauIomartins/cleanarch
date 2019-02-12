package com.paulorobertomartins.cleanarch.core.usecases.impl;

import com.paulorobertomartins.cleanarch.core.entities.Address;
import com.paulorobertomartins.cleanarch.core.entities.Movement;
import com.paulorobertomartins.cleanarch.core.entities.Product;
import com.paulorobertomartins.cleanarch.core.entities.Stock;
import com.paulorobertomartins.cleanarch.core.usecases.CreateOrUpdateStock;
import com.paulorobertomartins.cleanarch.core.usecases.InputStock;
import com.paulorobertomartins.cleanarch.core.usecases.exceptions.*;
import com.paulorobertomartins.cleanarch.core.usecases.requestmodel.InputStockRequest;
import com.paulorobertomartins.cleanarch.core.usecases.responsemodel.InputStockResponse;
import com.paulorobertomartins.cleanarch.gateways.AddressGateway;
import com.paulorobertomartins.cleanarch.gateways.MovementGateway;
import com.paulorobertomartins.cleanarch.gateways.ProductGateway;
import com.paulorobertomartins.cleanarch.gateways.StockGateway;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.function.Consumer;

@Transactional
@RequiredArgsConstructor
@Named
public class InputStockImpl implements InputStock {

    private final StockGateway stockGateway;
    private final AddressGateway addressGateway;
    private final ProductGateway productGateway;
    private final MovementGateway movementGateway;
    private final CreateOrUpdateStock createOrUpdateStock;

    @Override
    public void execute(InputStockRequest request, Consumer<InputStockResponse> consumer) {

        if (request.getAddressLabel() == null || request.getAddressLabel().trim().isEmpty()) {
            throw new AddresLabelEmptyException();
        }
        if (request.getProductEan() == null || request.getProductEan().trim().isEmpty()) {
            throw new ProductEanEmptyException();
        }
        if (request.getQuantity() == null) {
            throw new QuantityNullException();
        } else if (request.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
            throw new QuantityLessThanZeroException();
        }

        final Address address = addressGateway.findByLabel(request.getAddressLabel())
                .orElseThrow(InvalidAddressException::new);

        final Product product = productGateway.findByEan(request.getProductEan())
                .orElseThrow(InvalidProductException::new);

        final Stock savedStock = stockGateway.createOrUpdate(
                createOrUpdateStock.execute(address, product, request.getQuantity())
        );

        final Movement movement = movementGateway.create(Movement.newInputMovement(address, product, request.getQuantity()));

        consumer.accept(InputStockResponse.builder()
                .addressLabel(address.getLabel())
                .productEan(product.getEan())
                .quantity(request.getQuantity())
                .stockId(savedStock.getId())
                .movementId(movement.getId())
                .build());
    }
}
