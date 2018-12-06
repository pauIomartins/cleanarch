package com.paulorobertomartins.cleanarch.infra.web.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    @LocalServerPort
    private int randomServerPort;

    @Before
    public void setup() {
        RestAssured.port = randomServerPort;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void should_create_a_product() {

        final String productEan = "prod_xyz_001";
        final String productDescription = "Description of prod_xyz_001";
        final Map<String, String> product = new HashMap<>();
        product.put("ean", productEan);
        product.put("description", productDescription);

        given().contentType(ContentType.JSON).body(product)
                .when().post("/products")
                .then().assertThat().statusCode(201)
                .body("id", notNullValue())
                .body("ean", is(productEan))
                .body("description", is(productDescription));
    }
}