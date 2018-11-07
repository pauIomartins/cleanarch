package com.paulorobertomartins.cleanarch.infra.web.controller;

import com.paulorobertomartins.cleanarch.core.usecases.CreateAddress;
import com.paulorobertomartins.cleanarch.infra.web.controller.jsonresponse.CreateAddressJsonResponse;
import com.paulorobertomartins.cleanarch.infra.web.controller.payload.CreateAddressPayload;
import com.paulorobertomartins.cleanarch.infra.web.controller.presenter.CreateAddressReponsePresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
public class AddressController {

    private final CreateAddress createAddress;

    @GetMapping(path = "/hello")
    public String hello() {
        return "Hello Clean World!";
    }

    @ResponseStatus(CREATED)
    @PostMapping(path = "/addresses")
    public CreateAddressJsonResponse createAddress(@RequestBody final CreateAddressPayload payload) {
        final CreateAddressReponsePresenter presenter = new CreateAddressReponsePresenter();
        createAddress.execute(payload.toCreateAddressRequest(), presenter);
        return presenter.getJsonResponse();
    }
}
