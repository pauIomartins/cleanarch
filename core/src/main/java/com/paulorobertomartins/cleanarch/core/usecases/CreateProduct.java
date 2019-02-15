package com.paulorobertomartins.cleanarch.core.usecases;

import com.paulorobertomartins.cleanarch.core.usecases.requestmodel.CreateProductRequest;
import com.paulorobertomartins.cleanarch.core.usecases.responsemodel.CreateProductResponse;

@FunctionalInterface
public interface CreateProduct extends UseCase<CreateProductRequest, CreateProductResponse> {
}