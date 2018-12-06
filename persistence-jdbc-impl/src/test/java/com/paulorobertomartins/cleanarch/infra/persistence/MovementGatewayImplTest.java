package com.paulorobertomartins.cleanarch.infra.persistence;

import com.paulorobertomartins.cleanarch.core.entities.Address;
import com.paulorobertomartins.cleanarch.core.entities.Movement;
import com.paulorobertomartins.cleanarch.core.entities.Product;
import com.paulorobertomartins.cleanarch.gateways.AddressGateway;
import com.paulorobertomartins.cleanarch.gateways.MovementGateway;
import com.paulorobertomartins.cleanarch.gateways.ProductGateway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@JdbcTest
@SpringBootTest(classes = {AddressGatewayImpl.class, ProductGatewayImpl.class, MovementGatewayImpl.class})
public class MovementGatewayImplTest {

    @Autowired
    private AddressGateway addressGateway;
    @Autowired
    private ProductGateway productGateway;
    @Autowired
    private MovementGateway movementGateway;

    @Test
    public void should_create_transfer_movement() {

        final Address addressFrom = createAddress();
        final Address addressTo = createAddress();
        final Product product = createProduct();
        final BigDecimal quantity = BigDecimal.valueOf(Math.random());

        Movement movementSaved = movementGateway.create(Movement.builder()
                .addressFrom(addressFrom)
                .addressTo(addressTo)
                .product(product)
                .quantity(quantity)
                .type(Movement.MovementType.TRANSFER)
                .build());

        assertNotNull(movementSaved);
        assertNotNull(movementSaved.getId());
        assertEquals(movementSaved.getAddressFrom(), addressFrom);
        assertEquals(movementSaved.getAddressTo(), addressTo);
        assertEquals(movementSaved.getProduct(), product);
        assertEquals(movementSaved.getQuantity(), quantity);
    }

    private Product createProduct() {
        final String productEan = "product-" + ((Double) Math.random()).intValue();
        return productGateway.persist(new Product("Description of " + productEan, productEan));
    }

    private Address createAddress() {
        return addressGateway.persist(new Address("address-" + ((Double) Math.random()).intValue()));
    }
}