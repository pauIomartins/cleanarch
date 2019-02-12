package com.paulorobertomartins.cleanarch.core.usecases.impl;

import com.paulorobertomartins.cleanarch.core.entities.Product;
import com.paulorobertomartins.cleanarch.core.usecases.requestmodel.CreateProductRequest;
import com.paulorobertomartins.cleanarch.core.usecases.responsemodel.CreateProductResponse;
import com.paulorobertomartins.cleanarch.gateways.ProductGateway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CreateProductImplTest {

    @Mock
    private ProductGateway productGateway;

    @Test
    public void should_create_product() {

        final String productEan = "product-001";
        final Product product = new Product(567L, "Product 001", productEan);
        Mockito.when(productGateway.persist(new Product("Product 001", productEan))).thenReturn(product);

        final CreateProductImpl createProduct = new CreateProductImpl(productGateway);

        final CreateProductRequest request = CreateProductRequest.builder()
                .description("Product 001")
                .ean(productEan)
                .build();

        final Consumer<CreateProductResponse> consumer = (r) -> {
            assertEquals(r.getDescription(), product.getDescription());
            assertEquals(r.getEan(), product.getEan());
            assertEquals(r.getId(), product.getId());
        };

        createProduct.execute(request, consumer);
    }
}