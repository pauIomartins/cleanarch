package com.paulorobertomartins.cleanarch.infra.web.controller;

import com.paulorobertomartins.cleanarch.core.usecases.CreateProduct;
import com.paulorobertomartins.cleanarch.infra.web.controller.jsonresponse.CreateProductJsonResponse;
import com.paulorobertomartins.cleanarch.infra.web.controller.payload.CreateProductPayload;
import com.paulorobertomartins.cleanarch.infra.web.controller.presenter.CreateProductReponsePresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
public class ProductController {

    private final CreateProduct createProduct;

    @ResponseStatus(CREATED)
    @PostMapping(path = "/products")
    public CreateProductJsonResponse createAddress(@RequestBody final CreateProductPayload payload) {
        final CreateProductReponsePresenter presenter = new CreateProductReponsePresenter();
        createProduct.execute(payload.toCreateProductRequest(), presenter);
        return presenter.getJsonResponse();
    }
}
