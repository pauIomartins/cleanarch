package com.paulorobertomartins.cleanarch.core.usecases;

import com.paulorobertomartins.cleanarch.core.usecases.requestmodel.StockReservationRequest;
import com.paulorobertomartins.cleanarch.core.usecases.responsemodel.StockReservationResponse;

@FunctionalInterface
public interface DoStockReservation extends UseCase<StockReservationRequest, StockReservationResponse> {
}