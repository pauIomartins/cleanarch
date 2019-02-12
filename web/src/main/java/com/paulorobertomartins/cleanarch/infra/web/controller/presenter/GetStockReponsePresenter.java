package com.paulorobertomartins.cleanarch.infra.web.controller.presenter;

import com.paulorobertomartins.cleanarch.core.usecases.responsemodel.GetStockResponse;
import com.paulorobertomartins.cleanarch.infra.web.controller.jsonresponse.GetStockJsonResponse;
import lombok.Getter;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Getter
public class GetStockReponsePresenter implements Consumer<List<GetStockResponse>> {

    private List<GetStockJsonResponse> jsonResponse;

    @Override
    public void accept(List<GetStockResponse> response) {
        jsonResponse = response.stream().map(GetStockJsonResponse::new).collect(Collectors.toList());
    }
}