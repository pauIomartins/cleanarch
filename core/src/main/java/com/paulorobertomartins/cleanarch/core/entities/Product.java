package com.paulorobertomartins.cleanarch.core.entities;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Product {

    private Long id;
    @NonNull
    private String description;
    @NonNull
    private String ean;
}
