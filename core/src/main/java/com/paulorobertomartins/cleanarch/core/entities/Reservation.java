package com.paulorobertomartins.cleanarch.core.entities;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Reservation {

    private Long id;
    private Stock stock;
    private String uniqueIdentifier;
    private Double quantity;
}
