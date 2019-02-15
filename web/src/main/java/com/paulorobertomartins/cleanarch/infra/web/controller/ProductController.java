package com.paulorobertomartins.cleanarch.infra.web.controller;

import com.paulorobertomartins.cleanarch.core.usecases.CreateProduct;
import com.paulorobertomartins.cleanarch.core.usecases.GetStockByProduct;
import com.paulorobertomartins.cleanarch.core.usecases.requestmodel.GetStockByProductRequest;
import com.paulorobertomartins.cleanarch.infra.web.controller.jsonresponse.CreateProductJsonResponse;
import com.paulorobertomartins.cleanarch.infra.web.controller.jsonresponse.GetStockJsonResponse;
import com.paulorobertomartins.cleanarch.infra.web.controller.payload.CreateProductPayload;
import com.paulorobertomartins.cleanarch.infra.web.controller.presenter.CreateProductResponsePresenter;
import com.paulorobertomartins.cleanarch.infra.web.controller.presenter.GetStockResponsePresenter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
public class ProductController {

    private final CreateProduct createProduct;
    private final GetStockByProduct getStockByProduct;

    @ResponseStatus(CREATED)
    @PostMapping(path = "/products")
    public CreateProductJsonResponse createAddress(@RequestBody final CreateProductPayload payload) {
        final CreateProductResponsePresenter presenter = new CreateProductResponsePresenter();
        createProduct.execute(payload.toCreateProductRequest(), presenter);
        return presenter.getJsonResponse();
    }

    @GetMapping("/products/{productEan}/stock")
    public List<GetStockJsonResponse> getGetStockByProduct(@PathVariable("productEan") final String productEan) {
        final GetStockResponsePresenter presenter = new GetStockResponsePresenter();
        getStockByProduct.execute(GetStockByProductRequest.builder().productEan(productEan).build(), presenter);
        return presenter.getJsonResponse();
    }
}
