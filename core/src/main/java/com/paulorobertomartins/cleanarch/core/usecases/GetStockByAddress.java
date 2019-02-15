package com.paulorobertomartins.cleanarch.core.usecases;

import com.paulorobertomartins.cleanarch.core.usecases.requestmodel.GetStockByAddressRequest;
import com.paulorobertomartins.cleanarch.core.usecases.responsemodel.GetStockResponse;

import java.util.List;

@FunctionalInterface
public interface GetStockByAddress extends UseCase<GetStockByAddressRequest, List<GetStockResponse>> {
}