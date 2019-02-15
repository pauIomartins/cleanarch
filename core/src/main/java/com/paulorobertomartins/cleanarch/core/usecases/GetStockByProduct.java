package com.paulorobertomartins.cleanarch.core.usecases;

import com.paulorobertomartins.cleanarch.core.usecases.requestmodel.GetStockByProductRequest;
import com.paulorobertomartins.cleanarch.core.usecases.responsemodel.GetStockResponse;

import java.util.List;

@FunctionalInterface
public interface GetStockByProduct extends UseCase<GetStockByProductRequest, List<GetStockResponse>> {
}