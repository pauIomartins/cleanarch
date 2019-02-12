package com.paulorobertomartins.cleanarch.core.usecases.impl;

import com.paulorobertomartins.cleanarch.core.entities.Address;
import com.paulorobertomartins.cleanarch.core.entities.Movement;
import com.paulorobertomartins.cleanarch.core.entities.Product;
import com.paulorobertomartins.cleanarch.core.entities.Stock;
import com.paulorobertomartins.cleanarch.core.usecases.CreateOrUpdateStock;
import com.paulorobertomartins.cleanarch.core.usecases.TransferStock;
import com.paulorobertomartins.cleanarch.core.usecases.exceptions.*;
import com.paulorobertomartins.cleanarch.core.usecases.requestmodel.TransferStockRequest;
import com.paulorobertomartins.cleanarch.core.usecases.responsemodel.TransferStockResponse;
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
public class TransferStockImpl implements TransferStock {

    private final StockGateway stockGateway;
    private final AddressGateway addressGateway;
    private final ProductGateway productGateway;
    private final MovementGateway movementGateway;
    private final CreateOrUpdateStock createOrUpdateStock;

    @Override
    public void execute(TransferStockRequest request, Consumer<TransferStockResponse> consumer) {

        if (request.getAddressFromLabel() == null || request.getAddressFromLabel().trim().isEmpty()) {
            throw new AddresLabelEmptyException();
        }
        if (request.getAddressToLabel() == null || request.getAddressToLabel().trim().isEmpty()) {
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

        final Address addressFrom = addressGateway.findByLabel(request.getAddressFromLabel()).orElseThrow(InvalidAddressException::new);
        final Address addressTo = addressGateway.findByLabel(request.getAddressToLabel()).orElseThrow(InvalidAddressException::new);
        final Product product = productGateway.findByEan(request.getProductEan()).orElseThrow(InvalidProductException::new);
        final Stock stockFrom = stockGateway.findByAddressAndProduct(addressFrom, product).orElseThrow(StockNotExistsException::new);

        if (stockFrom.getQuantity().compareTo(request.getQuantity()) < 0) {
            throw new NotEnoughStockException();
        }

        final Stock savedStock = stockGateway.createOrUpdate(
                createOrUpdateStock.execute(addressTo, product, request.getQuantity())
        );

        stockFrom.decrementQuantity(request.getQuantity());
        stockGateway.update(stockFrom);

        if (stockFrom.isEmpty()) {
            stockGateway.remove(stockFrom);
        }

        final Movement movement = movementGateway.create(Movement.newTransferMovement(addressFrom, addressTo, product, request.getQuantity()));

        consumer.accept(TransferStockResponse.builder()
                .addressFromLabel(addressFrom.getLabel())
                .addressToLabel(addressTo.getLabel())
                .productEan(product.getEan())
                .quantity(request.getQuantity())
                .stockId(savedStock.getId())
                .movementId(movement.getId())
                .build());
    }
}
