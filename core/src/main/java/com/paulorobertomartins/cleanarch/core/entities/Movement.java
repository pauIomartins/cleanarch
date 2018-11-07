package com.paulorobertomartins.cleanarch.core.entities;

import lombok.Data;

@Data
public class Movement {

    private Long id;
    private Address addressFrom;
    private Address addressTo;
    private Product product;
    private Double quantity;
}
