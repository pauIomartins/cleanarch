package com.paulorobertomartins.cleanarch.core.entities;

import lombok.Data;

@Data
public class Stock {

    private Long id;
    private Product product;
    private Address address;
    private Double quantity;
}
