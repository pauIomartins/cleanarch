package com.paulorobertomartins.cleanarch.core.usecases.impl;

import com.paulorobertomartins.cleanarch.core.entities.Address;
import com.paulorobertomartins.cleanarch.core.usecases.CreateAddress;
import com.paulorobertomartins.cleanarch.core.usecases.requestmodel.CreateAddressRequest;
import com.paulorobertomartins.cleanarch.core.usecases.responsemodel.CreateAddressResponse;
import com.paulorobertomartins.cleanarch.gateways.AddressGateway;
import lombok.RequiredArgsConstructor;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Transactional
@Named
public class CreateAddressImpl implements CreateAddress {

    private final AddressGateway addressGateway;

    @Override
    public void execute(CreateAddressRequest request, Consumer<CreateAddressResponse> consumer) {
        Address addressSaved = addressGateway.persist(new Address(request.getAddressLabel()));
        consumer.accept(CreateAddressResponse.builder()
                .id(addressSaved.getId())
                .label(addressSaved.getLabel())
                .build());
    }
}
