package com.paulorobertomartins.cleanarch.core.usecases.impl;

import com.paulorobertomartins.cleanarch.core.entities.Address;
import com.paulorobertomartins.cleanarch.core.entities.Movement;
import com.paulorobertomartins.cleanarch.core.entities.Product;
import com.paulorobertomartins.cleanarch.core.entities.Stock;
import com.paulorobertomartins.cleanarch.core.usecases.TransferStock;
import com.paulorobertomartins.cleanarch.core.usecases.exceptions.InvalidAddressException;
import com.paulorobertomartins.cleanarch.core.usecases.exceptions.InvalidProductException;
import com.paulorobertomartins.cleanarch.core.usecases.exceptions.NotEnoughStockException;
import com.paulorobertomartins.cleanarch.core.usecases.exceptions.StockNotExistsException;
import com.paulorobertomartins.cleanarch.core.usecases.requestmodel.TransferStockRequest;
import com.paulorobertomartins.cleanarch.core.usecases.responsemodel.TransferStockResponse;
import com.paulorobertomartins.cleanarch.gateways.AddressGateway;
import com.paulorobertomartins.cleanarch.gateways.MovementGateway;
import com.paulorobertomartins.cleanarch.gateways.ProductGateway;
import com.paulorobertomartins.cleanarch.gateways.StockGateway;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.function.Consumer;

@Transactional
@RequiredArgsConstructor
@Named
public class TransferStockImpl implements TransferStock {

    private final StockGateway stockGateway;
    private final AddressGateway addressGateway;
    private final ProductGateway productGateway;
    private final MovementGateway movementGateway;

    @Override
    public void execute(TransferStockRequest request, Consumer<TransferStockResponse> consumer) {

        final Address addressFrom = addressGateway.findByLabel(request.getAddressFromLabel()).orElseThrow(InvalidAddressException::new);
        final Address addressTo = addressGateway.findByLabel(request.getAddressToLabel()).orElseThrow(InvalidAddressException::new);
        final Product product = productGateway.findByEan(request.getProductEan()).orElseThrow(InvalidProductException::new);
        final Stock stockFrom = stockGateway.findByAddressAndProduct(addressFrom, product).orElseThrow(StockNotExistsException::new);

        if (stockFrom.getQuantity().compareTo(request.getQuantity()) < 0) {
            throw new NotEnoughStockException();
        }

        final Optional<Stock> stockTo = stockGateway.findByAddressAndProduct(addressTo, product);

        final Stock savedStock;
        if (stockTo.isPresent()) {
            final Stock stock = stockTo.get();
            stock.incrementQuantity(request.getQuantity());
            savedStock = stockGateway.update(stock);
        } else {
            savedStock = stockGateway.create(new Stock(addressTo, product, request.getQuantity()));
        }

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
