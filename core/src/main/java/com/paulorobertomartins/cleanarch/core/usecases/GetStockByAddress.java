package com.paulorobertomartins.cleanarch.core.usecases;

import com.paulorobertomartins.cleanarch.core.usecases.requestmodel.GetStockRequest;
import com.paulorobertomartins.cleanarch.core.usecases.responsemodel.GetStockResponse;

@FunctionalInterface
public interface GetStockByAddress extends Usecase<GetStockRequest, GetStockResponse> {
}