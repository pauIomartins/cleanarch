package com.paulorobertomartins.cleanarch.infra.web.controller;

import com.paulorobertomartins.cleanarch.core.usecases.GetAllStock;
import com.paulorobertomartins.cleanarch.infra.web.controller.jsonresponse.GetStockJsonResponse;
import com.paulorobertomartins.cleanarch.infra.web.controller.presenter.GetStockResponsePresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class StockController {

    private final GetAllStock getAllStock;

    @GetMapping("/stock")
    public List<GetStockJsonResponse> getGetStockByProduct() {
        final GetStockResponsePresenter presenter = new GetStockResponsePresenter();
        getAllStock.execute(null, presenter);
        return presenter.getJsonResponse();
    }
}
