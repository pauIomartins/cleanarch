package com.paulorobertomartins.cleanarch.gateways;

import com.paulorobertomartins.cleanarch.core.entities.Address;

import java.util.Optional;

public interface AddressGateway extends EntityGateway<Address> {

    Optional<Address> findById(final long addressId);

    Optional<Address> findByLabel(final String addressLabel);
}
