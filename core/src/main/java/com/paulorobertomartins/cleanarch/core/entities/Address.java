package com.paulorobertomartins.cleanarch.core.entities;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Address {

    private Long id;
    @NonNull
    private String label;
}
