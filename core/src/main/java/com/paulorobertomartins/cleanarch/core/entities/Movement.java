package com.paulorobertomartins.cleanarch.core.entities;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Movement {

    private Long id;
    private Address addressFrom;
    private Address addressTo;
    @NonNull
    private Product product;
    @NonNull
    private Double quantity;
    @NonNull
    private MovementType type;

    public Movement(Address addressTo, Product product, Double quantity, MovementType type) {
        this.addressTo = addressTo;
        this.product = product;
        this.quantity = quantity;
        this.type = type;
    }

    public static Movement newInputMovement(Address addressTo, Product product, Double quantity) {
        return new Movement(addressTo, product, quantity, MovementType.INPUT);
    }

    public enum MovementType {
        INPUT,
        OUTPUT,
        TRANSFER;
    }
}
