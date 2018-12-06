package com.paulorobertomartins.cleanarch.infra.web.controller;

import com.paulorobertomartins.cleanarch.core.usecases.CreateAddress;
import com.paulorobertomartins.cleanarch.core.usecases.CreateProduct;
import com.paulorobertomartins.cleanarch.core.usecases.InputStock;
import com.paulorobertomartins.cleanarch.core.usecases.requestmodel.CreateAddressRequest;
import com.paulorobertomartins.cleanarch.core.usecases.requestmodel.CreateProductRequest;
import com.paulorobertomartins.cleanarch.core.usecases.requestmodel.InputStockRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.function.Consumer;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovementControllerTest {

    @LocalServerPort
    private int randomServerPort;

    @Autowired
    private CreateAddress createAddress;

    @Autowired
    private CreateProduct createProduct;

    @Autowired
    private InputStock inputStock;

    @Before
    public void setup() {
        RestAssured.port = randomServerPort;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void should_create_an_input_movement() {

        final String addressToLabel = "to-001";
        final String productEan = "xyz_001";
        final Integer quantity = 10;

        final Consumer consumer = (response) -> {
        };

        createAddress.execute(CreateAddressRequest.builder()
                .addressLabel(addressToLabel)
                .build(), consumer);

        createProduct.execute(CreateProductRequest.builder()
                .ean(productEan)
                .description("Desc " + productEan)
                .build(), consumer);

        MovementBody movementBody = new MovementBody(null, addressToLabel, productEan, quantity, "input");

        given().contentType(ContentType.JSON).body(movementBody)
                .when().post("/movements")
                .then().assertThat().statusCode(201).contentType(ContentType.JSON)
                .body("stock_id", notNullValue())
                .body("movement_id", notNullValue())
                .body("address_from", nullValue())
                .body("address_to", is(addressToLabel))
                .body("product_ean", is(productEan))
                .body("quantity", is(10))
                .body("type", is("input"));
    }

    @Test
    public void should_create_a_transfer_movement() {

        final String addressFromLabel = "from-001";
        final String addressToLabel = "to-001";
        final String productEan = "xyz_001";
        final Integer quantity = 10;

        final Consumer consumer = (response) -> {
        };

        createAddress.execute(CreateAddressRequest.builder()
                .addressLabel(addressFromLabel)
                .build(), consumer);

        createAddress.execute(CreateAddressRequest.builder()
                .addressLabel(addressToLabel)
                .build(), consumer);

        createProduct.execute(CreateProductRequest.builder()
                .ean(productEan)
                .description("Desc " + productEan)
                .build(), consumer);

        inputStock.execute(InputStockRequest.builder()
                .addressLabel(addressFromLabel)
                .productEan(productEan)
                .quantity(new BigDecimal(100))
                .build(), consumer);

        MovementBody movementBody = new MovementBody(addressFromLabel, addressToLabel, productEan, quantity, "transfer");

        given().contentType(ContentType.JSON).body(movementBody)
                .when().post("/movements")
                .then().assertThat().statusCode(201).contentType(ContentType.JSON)
                .body("stock_id", notNullValue())
                .body("movement_id", notNullValue())
                .body("address_from", is(addressFromLabel))
                .body("address_to", is(addressToLabel))
                .body("product_ean", is(productEan))
                .body("quantity", is(10))
                .body("type", is("transfer"));
    }

    class MovementBody {

        private final String address_from;
        private final String address_to;
        private final String product_ean;
        private final Integer quantity;
        private final String type;

        public MovementBody(String address_from, String address_to, String product_ean, Integer quantity, String type) {
            this.address_from = address_from;
            this.address_to = address_to;
            this.product_ean = product_ean;
            this.quantity = quantity;
            this.type = type;
        }

        public String getAddress_from() {
            return address_from;
        }

        public String getAddress_to() {
            return address_to;
        }

        public String getProduct_ean() {
            return product_ean;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public String getType() {
            return type;
        }
    }
}