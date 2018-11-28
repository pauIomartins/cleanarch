package com.paulorobertomartins.cleanarch.core.entities;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Stock {

    private Long id;
    @NonNull
    private Address address;
    @NonNull
    private Product product;
    @NonNull
    private Double quantity;
}
