package com.paulorobertomartins.cleanarch.core.entities;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Product {

    private Long id;
    @NonNull
    private String description;
    @NonNull
    private String ean;
}
