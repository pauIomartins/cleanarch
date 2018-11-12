package com.paulorobertomartins.cleanarch.core.usecases.impl;

import com.paulorobertomartins.cleanarch.core.entities.Address;
import com.paulorobertomartins.cleanarch.core.entities.Movement;
import com.paulorobertomartins.cleanarch.core.entities.Product;
import com.paulorobertomartins.cleanarch.core.entities.Stock;
import com.paulorobertomartins.cleanarch.core.usecases.InputStock;
import com.paulorobertomartins.cleanarch.core.usecases.exceptions.InvalidAddressException;
import com.paulorobertomartins.cleanarch.core.usecases.exceptions.InvalidProductException;
import com.paulorobertomartins.cleanarch.core.usecases.requestmodel.InputStockRequest;
import com.paulorobertomartins.cleanarch.core.usecases.responsemodel.InputStockResponse;
import com.paulorobertomartins.cleanarch.gateways.AddressGateway;
import com.paulorobertomartins.cleanarch.gateways.MovementGateway;
import com.paulorobertomartins.cleanarch.gateways.ProductGateway;
import com.paulorobertomartins.cleanarch.gateways.StockGateway;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Transactional
@Named
public class InputStockImpl implements InputStock {

    private final StockGateway stockGateway;
    private final AddressGateway addressGateway;
    private final ProductGateway productGateway;
    private final MovementGateway movementGateway;

    @Override
    public void execute(InputStockRequest request, Consumer<InputStockResponse> consumer) {

        Address address = addressGateway.findByLabel(request.getAddressLabel())
                .orElseThrow(InvalidAddressException::new);

        Product product = productGateway.findByEan(request.getProductEan())
                .orElseThrow(InvalidProductException::new);

        Optional<Stock> stockOptional = stockGateway.findByAddressAndProduct(address, product);

        Stock savedStock;
        if (stockOptional.isPresent()) {
            Stock updateStock = stockOptional.get();
            updateStock.setQuantity(updateStock.getQuantity() + request.getQuantity());
            savedStock = stockGateway.update(updateStock);
        } else {
            savedStock = stockGateway.create(new Stock(address, product, request.getQuantity()));
        }

        Movement movement = movementGateway.create(Movement.newInputMovement(address, product, request.getQuantity()));
        movementGateway.create(movement);

        consumer.accept(InputStockResponse.builder()
                .addressLabel(address.getLabel())
                .productEan(product.getEan())
                .quantity(savedStock.getQuantity())
                .stockId(savedStock.getId())
                .build());
    }
}
