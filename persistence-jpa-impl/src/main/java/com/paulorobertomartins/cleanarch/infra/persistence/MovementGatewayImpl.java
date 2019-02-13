package com.paulorobertomartins.cleanarch.infra.persistence;

import com.paulorobertomartins.cleanarch.core.entities.Movement;
import com.paulorobertomartins.cleanarch.gateways.MovementGateway;
import com.paulorobertomartins.cleanarch.infra.persistence.entities.AddressEntity;
import com.paulorobertomartins.cleanarch.infra.persistence.entities.MovementEntity;
import com.paulorobertomartins.cleanarch.infra.persistence.entities.ProductEntity;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;
import javax.persistence.EntityManager;

@RequiredArgsConstructor
@Named
public class MovementGatewayImpl implements MovementGateway {

    private final EntityManager entityManager;

    @Override
    public Movement create(final Movement movement) {

        final MovementEntity movementEntity = new MovementEntity();
        if (movement.getAddressFrom() != null) {
            movementEntity.setAddressEntityFrom(new AddressEntity(movement.getAddressFrom().getId()));
        }
        if (movement.getAddressTo() != null) {
            movementEntity.setAddressEntityTo(new AddressEntity(movement.getAddressTo().getId()));
        }
        movementEntity.setProductEntity(new ProductEntity(movement.getProduct().getId()));
        movementEntity.setQuantity(movement.getQuantity());
        movementEntity.setType(movement.getType().name());

        entityManager.persist(movementEntity);
        entityManager.flush();

        return new Movement(movementEntity.getId(),
                movement.getAddressFrom(),
                movement.getAddressTo(),
                movement.getProduct(),
                movementEntity.getQuantity(),
                Movement.MovementType.valueOf(movementEntity.getType())
        );
    }
}
