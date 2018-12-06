package com.paulorobertomartins.cleanarch.infra.persistence;

import com.paulorobertomartins.cleanarch.core.entities.Address;
import com.paulorobertomartins.cleanarch.gateways.AddressGateway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@JdbcTest
@SpringBootTest(classes = {AddressGatewayImpl.class})
public class AddressGatewayImplTest {

    @Autowired
    private AddressGateway addressGateway;

    @Test
    public void should_persist_address() {
        final String addressLabel = createAddressLabel();
        final Address addressSaved = createAddress(addressLabel);
        assertNotNull(addressSaved);
        assertNotNull(addressSaved.getId());
        assertEquals(addressSaved.getLabel(), addressLabel);
    }

    @Test
    public void should_find_address_by_id() {
        final String addressLabel = createAddressLabel();
        final Address addressSaved = createAddress(addressLabel);
        final Address addressFind = addressGateway.findById(addressSaved.getId()).orElse(null);
        assertNotNull(addressFind);
        assertEquals(addressSaved.getId(), addressFind.getId());
        assertEquals(addressFind.getLabel(), addressLabel);
    }

    @Test
    public void should_find_address_by_label() {
        final String addressLabel = createAddressLabel();
        final Address addressSaved = createAddress(addressLabel);
        final Address addressFind = addressGateway.findByLabel(addressLabel).orElse(null);
        assertNotNull(addressFind);
        assertEquals(addressSaved.getId(), addressFind.getId());
        assertEquals(addressFind.getLabel(), addressLabel);
    }

    private String createAddressLabel() {
        return "address-" + ((Double) Math.random()).intValue();
    }

    private Address createAddress(final String addressLabel) {
        return addressGateway.persist(new Address(addressLabel));
    }
}