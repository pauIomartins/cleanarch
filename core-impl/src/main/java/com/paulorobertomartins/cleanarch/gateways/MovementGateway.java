package com.paulorobertomartins.cleanarch.gateways;

import com.paulorobertomartins.cleanarch.core.entities.Movement;

public interface MovementGateway {

    Movement create(final Movement movement);
}
