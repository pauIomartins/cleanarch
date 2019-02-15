package com.paulorobertomartins.cleanarch.core.usecases;

import com.paulorobertomartins.cleanarch.core.usecases.responsemodel.GetStockResponse;

import java.util.List;

@FunctionalInterface
public interface GetAllStock extends UseCase<Void, List<GetStockResponse>> {
}