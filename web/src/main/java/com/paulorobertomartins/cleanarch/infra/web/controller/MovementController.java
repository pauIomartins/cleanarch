package com.paulorobertomartins.cleanarch.infra.web.controller;

import com.paulorobertomartins.cleanarch.core.usecases.InputStock;
import com.paulorobertomartins.cleanarch.core.usecases.requestmodel.InputStockRequest;
import com.paulorobertomartins.cleanarch.infra.web.controller.jsonresponse.CreateMovementJsonResponse;
import com.paulorobertomartins.cleanarch.infra.web.controller.payload.CreateMovementPayload;
import com.paulorobertomartins.cleanarch.infra.web.controller.presenter.InputStockResponsePresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
public class MovementController {

    private final InputStock inputStock;

    @ResponseStatus(CREATED)
    @PostMapping(path = "/movements")
    public CreateMovementJsonResponse createAddress(@RequestBody final CreateMovementPayload payload) {
        final InputStockResponsePresenter presenter = new InputStockResponsePresenter();
        inputStock.execute((InputStockRequest) payload.toRequest(), presenter);
        return presenter.getJsonResponse();
    }
}
