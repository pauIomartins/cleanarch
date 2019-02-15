package com.paulorobertomartins.cleanarch.core.usecases;

import com.paulorobertomartins.cleanarch.core.usecases.requestmodel.OutputStockRequest;
import com.paulorobertomartins.cleanarch.core.usecases.responsemodel.OutputStockResponse;

@FunctionalInterface
public interface OutputStock extends UseCase<OutputStockRequest, OutputStockResponse> {
}