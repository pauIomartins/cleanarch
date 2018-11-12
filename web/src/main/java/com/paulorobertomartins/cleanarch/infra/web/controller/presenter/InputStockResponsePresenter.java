package com.paulorobertomartins.cleanarch.infra.web.controller.presenter;

import com.paulorobertomartins.cleanarch.core.usecases.responsemodel.InputStockResponse;
import com.paulorobertomartins.cleanarch.infra.web.controller.jsonresponse.CreateMovementJsonResponse;
import lombok.Getter;

import java.util.function.Consumer;

@Getter
public class InputStockResponsePresenter implements Consumer<InputStockResponse> {

    private CreateMovementJsonResponse jsonResponse;

    @Override
    public void accept(InputStockResponse response) {
        jsonResponse = CreateMovementJsonResponse.builder()
                .stockId(response.getStockId())
                .addressFrom(null)
                .addressTo(response.getAddressLabel())
                .productEan(response.getProductEan())
                .quantity(response.getQuantity())
                .type("input")
                .build();
    }
}