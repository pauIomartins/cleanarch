package com.paulorobertomartins.cleanarch.infra.web.controller.presenter;

import com.paulorobertomartins.cleanarch.core.usecases.responsemodel.TransferStockResponse;
import com.paulorobertomartins.cleanarch.infra.web.controller.jsonresponse.CreateMovementJsonResponse;
import lombok.Getter;

import java.util.function.Consumer;

@Getter
public class TransferStockResponsePresenter implements Consumer<TransferStockResponse> {

    private CreateMovementJsonResponse jsonResponse;

    @Override
    public void accept(TransferStockResponse response) {
        jsonResponse = CreateMovementJsonResponse.builder()
                .stockId(response.getMovementId())
                .movementId(response.getStockId())
                .addressFrom(response.getAddressFromLabel())
                .addressTo(response.getAddressToLabel())
                .productEan(response.getProductEan())
                .quantity(response.getQuantity())
                .type("transfer")
                .build();
    }
}