package com.paulorobertomartins.cleanarch.core.entities;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

@Builder(toBuilder = true)
@Value
public class Movement {

    private final Long id;
    private final Address addressFrom;
    private final Address addressTo;
    @NonNull
    private final Product product;
    @NonNull
    private final BigDecimal quantity;
    @NonNull
    private final MovementType type;

    public Movement(Address addressTo, Product product, BigDecimal quantity, MovementType type) {
        this.id = null;
        this.addressFrom = null;
        this.addressTo = addressTo;
        this.product = product;
        this.quantity = quantity;
        this.type = type;
    }

    public Movement(Address addressFrom, Address addressTo, Product product, BigDecimal quantity, MovementType type) {
        this.id = null;
        this.addressFrom = addressFrom;
        this.addressTo = addressTo;
        this.product = product;
        this.quantity = quantity;
        this.type = type;
    }

    public Movement(Long id, Address addressFrom, Address addressTo, Product product, BigDecimal quantity, MovementType type) {
        this.id = id;
        this.addressFrom = addressFrom;
        this.addressTo = addressTo;
        this.product = product;
        this.quantity = quantity;
        this.type = type;
    }

    public static Movement newInputMovement(Address addressTo, Product product, BigDecimal quantity) {
        return new Movement(addressTo, product, quantity, MovementType.INPUT);
    }

    public static Movement newTransferMovement(Address addressFrom, Address addressTo, Product product, BigDecimal quantity) {
        return new Movement(addressFrom, addressTo, product, quantity, MovementType.TRANSFER);
    }

    public enum MovementType {
        INPUT,
        OUTPUT,
        TRANSFER
    }
}
