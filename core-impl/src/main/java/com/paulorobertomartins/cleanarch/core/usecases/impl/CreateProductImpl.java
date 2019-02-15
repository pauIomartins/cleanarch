package com.paulorobertomartins.cleanarch.core.usecases.impl;

import com.paulorobertomartins.cleanarch.core.entities.Product;
import com.paulorobertomartins.cleanarch.core.usecases.CreateProduct;
import com.paulorobertomartins.cleanarch.core.usecases.requestmodel.CreateProductRequest;
import com.paulorobertomartins.cleanarch.core.usecases.responsemodel.CreateProductResponse;
import com.paulorobertomartins.cleanarch.gateways.ProductGateway;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Transactional
@Named
public class CreateProductImpl implements CreateProduct {

    private final ProductGateway productGateway;

    @Override
    public void execute(CreateProductRequest request, Consumer<CreateProductResponse> consumer) {
        final Product productSaved = productGateway.persist(new Product(request.getDescription(), request.getEan()));
        consumer.accept(CreateProductResponse.builder()
                .id(productSaved.getId())
                .description(productSaved.getDescription())
                .ean(productSaved.getEan())
                .build());
    }
}
