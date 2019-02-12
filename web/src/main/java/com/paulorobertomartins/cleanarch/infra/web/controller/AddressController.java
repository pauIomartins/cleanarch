package com.paulorobertomartins.cleanarch.infra.web.controller;

import com.paulorobertomartins.cleanarch.core.usecases.CreateAddress;
import com.paulorobertomartins.cleanarch.core.usecases.GetStockByAddress;
import com.paulorobertomartins.cleanarch.core.usecases.requestmodel.GetStockByAddressRequest;
import com.paulorobertomartins.cleanarch.infra.web.controller.jsonresponse.CreateAddressJsonResponse;
import com.paulorobertomartins.cleanarch.infra.web.controller.jsonresponse.GetStockJsonResponse;
import com.paulorobertomartins.cleanarch.infra.web.controller.payload.CreateAddressPayload;
import com.paulorobertomartins.cleanarch.infra.web.controller.presenter.CreateAddressReponsePresenter;
import com.paulorobertomartins.cleanarch.infra.web.controller.presenter.GetStockReponsePresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
public class AddressController {

    private final CreateAddress createAddress;
    private final GetStockByAddress getStockByAddress;

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

    @GetMapping("/addresses/{addressLabel}/stock")
    public List<GetStockJsonResponse> getGetStockByProduct(@PathVariable("addressLabel") final String addressLabel) {
        final GetStockReponsePresenter presenter = new GetStockReponsePresenter();
        getStockByAddress.execute(GetStockByAddressRequest.builder().addressLabel(addressLabel).build(), presenter);
        return presenter.getJsonResponse();
    }
}
