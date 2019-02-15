package com.paulorobertomartins.cleanarch.infra.web.controller.presenter;

import com.paulorobertomartins.cleanarch.core.usecases.responsemodel.CreateProductResponse;
import com.paulorobertomartins.cleanarch.infra.web.controller.jsonresponse.CreateProductJsonResponse;
import lombok.Getter;

import java.util.function.Consumer;

@Getter
public class CreateProductResponsePresenter implements Consumer<CreateProductResponse> {

    private CreateProductJsonResponse jsonResponse;

    @Override
    public void accept(CreateProductResponse response) {
        jsonResponse = new CreateProductJsonResponse(response);
    }
}