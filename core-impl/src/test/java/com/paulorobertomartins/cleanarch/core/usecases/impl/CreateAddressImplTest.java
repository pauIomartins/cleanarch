package com.paulorobertomartins.cleanarch.core.usecases.impl;

import com.paulorobertomartins.cleanarch.core.entities.Address;
import com.paulorobertomartins.cleanarch.core.usecases.requestmodel.CreateAddressRequest;
import com.paulorobertomartins.cleanarch.core.usecases.responsemodel.CreateAddressResponse;
import com.paulorobertomartins.cleanarch.gateways.AddressGateway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CreateAddressImplTest {

    @Mock
    private AddressGateway addressGateway;

    @Test
    public void should_create_address() {

        final String addressLabel = "address-001";
        final Address address = new Address(123L, addressLabel);
        Mockito.when(addressGateway.persist(new Address(addressLabel))).thenReturn(address);

        final CreateAddressImpl createAddress = new CreateAddressImpl(addressGateway);

        final CreateAddressRequest request = CreateAddressRequest.builder()
                .addressLabel(addressLabel)
                .build();

        final Consumer<CreateAddressResponse> consumer = (r) -> {
            assertEquals(r.getLabel(), addressLabel);
        };

        createAddress.execute(request, consumer);
    }
}