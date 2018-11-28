package com.paulorobertomartins.cleanarch.core.entities;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Address {

    private Long id;
    @NonNull
    private String label;
}
