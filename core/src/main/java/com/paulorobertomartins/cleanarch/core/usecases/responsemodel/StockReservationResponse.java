package com.paulorobertomartins.cleanarch.core.usecases.responsemodel;

import lombok.*;

@Builder(toBuilder = true)
@Value
public class StockReservationResponse {

    private final Long reservationId;
    private final String reservationIdentifier;
    private final String addressLabel;
    private final String productEan;
    private final Double quantity;
}
