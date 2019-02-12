package com.paulorobertomartins.cleanarch.core.usecases.impl;

import com.paulorobertomartins.cleanarch.core.entities.Product;
import com.paulorobertomartins.cleanarch.core.usecases.GetStockByProduct;
import com.paulorobertomartins.cleanarch.core.usecases.exceptions.InvalidProductException;
import com.paulorobertomartins.cleanarch.core.usecases.exceptions.ProductEanEmptyException;
import com.paulorobertomartins.cleanarch.core.usecases.requestmodel.GetStockByProductRequest;
import com.paulorobertomartins.cleanarch.core.usecases.responsemodel.GetStockResponse;
import com.paulorobertomartins.cleanarch.gateways.ProductGateway;
import com.paulorobertomartins.cleanarch.gateways.StockGateway;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Named
@Transactional
@RequiredArgsConstructor
public class GetStockByProductImpl implements GetStockByProduct {

    private final ProductGateway productGateway;
    private final StockGateway stockGateway;

    @Override
    public void execute(GetStockByProductRequest request, Consumer<List<GetStockResponse>> consumer) {

        if (request.getProductEan() == null || request.getProductEan().trim().isEmpty()) {
            throw new ProductEanEmptyException();
        }
        final Product product = productGateway.findByEan(request.getProductEan())
                .orElseThrow(InvalidProductException::new);

        consumer.accept(stockGateway.findByProduct(product).stream().map(s ->
                GetStockResponse.builder()
                        .id(s.getId())
                        .addressLabel(s.getAddress().getLabel())
                        .productEan(s.getProduct().getEan())
                        .quantity(s.getQuantity())
                        .build()
        ).collect(Collectors.toList()));
    }
}
