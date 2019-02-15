package com.paulorobertomartins.cleanarch.infra.web.controller.presenter;

import com.paulorobertomartins.cleanarch.core.usecases.responsemodel.CreateAddressResponse;
import com.paulorobertomartins.cleanarch.infra.web.controller.jsonresponse.CreateAddressJsonResponse;
import lombok.Getter;

import java.util.function.Consumer;

@Getter
public class CreateAddressResponsePresenter implements Consumer<CreateAddressResponse> {

    private CreateAddressJsonResponse jsonResponse;

    @Override
    public void accept(CreateAddressResponse response) {
        jsonResponse = new CreateAddressJsonResponse(response);
    }
}