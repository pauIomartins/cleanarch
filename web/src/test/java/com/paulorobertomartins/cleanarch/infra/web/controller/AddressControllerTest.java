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
public class AddressControllerTest {

    @LocalServerPort
    private int randomServerPort;

    @Before
    public void setup() {
        RestAssured.port = randomServerPort;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void should_get_hello_clean_arch() {
        given().when().get("/hello")
                .then().assertThat().statusCode(200).body(containsString("Hello Clean World!"));
    }

    @Test
    public void should_create_an_address() {

        final String addressLabel = "address_001";
        final Map<String, String> address = new HashMap<>();
        address.put("address_label", addressLabel);

        given().contentType(ContentType.JSON).body(address)
                .when().post("/addresses")
                .then().assertThat().statusCode(201).body("id", notNullValue()).body("label", is(addressLabel));
    }
}