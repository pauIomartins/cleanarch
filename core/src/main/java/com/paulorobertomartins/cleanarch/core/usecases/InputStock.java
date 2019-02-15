package com.paulorobertomartins.cleanarch.core.usecases;

import com.paulorobertomartins.cleanarch.core.usecases.requestmodel.InputStockRequest;
import com.paulorobertomartins.cleanarch.core.usecases.responsemodel.InputStockResponse;

@FunctionalInterface
public interface InputStock extends UseCase<InputStockRequest, InputStockResponse> {
}