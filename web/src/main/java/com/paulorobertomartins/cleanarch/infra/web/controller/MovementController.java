package com.paulorobertomartins.cleanarch.infra.web.controller;

import com.paulorobertomartins.cleanarch.core.usecases.InputStock;
import com.paulorobertomartins.cleanarch.core.usecases.TransferStock;
import com.paulorobertomartins.cleanarch.core.usecases.requestmodel.InputStockRequest;
import com.paulorobertomartins.cleanarch.core.usecases.requestmodel.TransferStockRequest;
import com.paulorobertomartins.cleanarch.infra.web.controller.jsonresponse.CreateMovementJsonResponse;
import com.paulorobertomartins.cleanarch.infra.web.controller.payload.ExecuteInputMovementPayload;
import com.paulorobertomartins.cleanarch.infra.web.controller.payload.ExecuteMovementPayload;
import com.paulorobertomartins.cleanarch.infra.web.controller.payload.ExecuteTransferMovementPayload;
import com.paulorobertomartins.cleanarch.infra.web.controller.presenter.InputStockResponsePresenter;
import com.paulorobertomartins.cleanarch.infra.web.controller.presenter.TransferStockResponsePresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
public class MovementController {

    private final InputStock inputStock;
    private final TransferStock transferStock;

    @ResponseStatus(CREATED)
    @PostMapping(path = "/movements")
    public CreateMovementJsonResponse executeMovement(@RequestBody final ExecuteMovementPayload payload) {

        if (payload instanceof ExecuteInputMovementPayload) {

            final InputStockResponsePresenter presenter = new InputStockResponsePresenter();
            inputStock.execute((InputStockRequest) payload.toRequest(), presenter);
            return presenter.getJsonResponse();

        } else if (payload instanceof ExecuteTransferMovementPayload) {

            final TransferStockResponsePresenter presenter = new TransferStockResponsePresenter();
            transferStock.execute((TransferStockRequest) payload.toRequest(), presenter);
            return presenter.getJsonResponse();

        } else {
            throw new NotImplementedException();
        }
    }
}
