package com.paulorobertomartins.cleanarch.core.entities;

import lombok.Data;

@Data
public class Reservation {

    private Long id;
    private Stock stock;
    private String uniqueIdentifier;
    private Double quantity;
}
