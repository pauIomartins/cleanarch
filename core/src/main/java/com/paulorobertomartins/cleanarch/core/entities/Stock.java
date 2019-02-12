package com.paulorobertomartins.cleanarch.core.entities;

import lombok.*;

import java.math.BigDecimal;

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
    private BigDecimal quantity;

    public void incrementQuantity(final BigDecimal quantity) {
        this.quantity = this.quantity.add(quantity);
    }

    public void decrementQuantity(final BigDecimal quantity) {
        this.quantity = this.quantity.subtract(quantity);
    }

    public boolean isEmpty() {
        return this.quantity.compareTo(BigDecimal.ZERO) <= 0;
    }
}
