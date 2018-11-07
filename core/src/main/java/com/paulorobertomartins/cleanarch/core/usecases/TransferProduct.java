package com.paulorobertomartins.cleanarch.core.usecases;

import com.paulorobertomartins.cleanarch.core.usecases.requestmodel.TransferStockRequest;
import com.paulorobertomartins.cleanarch.core.usecases.responsemodel.TransferStockResponse;

@FunctionalInterface
public interface TransferProduct extends Usecase<TransferStockRequest, TransferStockResponse> {
}