package com.paulorobertomartins.cleanarch.infra.persistence;

import com.paulorobertomartins.cleanarch.core.entities.Product;
import com.paulorobertomartins.cleanarch.gateways.ProductGateway;
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
@SpringBootTest(classes = {ProductGatewayImpl.class})
public class ProductGatewayImplTest {

    @Autowired
    private ProductGateway productGateway;


    @Test
    public void should_persist_address() {
        final String productEan = createProductEan();
        final Product productSaved = createProduct(productEan);
        assertNotNull(productSaved);
        assertNotNull(productSaved.getId());
        assertEquals(productSaved.getDescription(), getProductDescription(productEan));
        assertEquals(productSaved.getEan(), productEan);
    }

    @Test
    public void should_find_product_by_id() {
        final String productEan = createProductEan();
        final Product productSaved = createProduct(productEan);
        final Product productFind = productGateway.findById(productSaved.getId()).orElse(null);
        assertNotNull(productFind);
        assertEquals(productFind.getId(), productSaved.getId());
        assertEquals(productFind.getEan(), productEan);
    }

    @Test
    public void should_find_product_by_label() {
        final String productEan = createProductEan();
        final Product productSaved = createProduct(productEan);
        final Product productFind = productGateway.findByEan(productEan).orElse(null);
        assertNotNull(productFind);
        assertEquals(productFind.getId(), productSaved.getId());
        assertEquals(productFind.getEan(), productEan);
    }

    private String createProductEan() {
        return "product-" + ((Double) Math.random()).intValue();
    }

    private Product createProduct(final String productEan) {
        return productGateway.persist(new Product(getProductDescription(productEan), productEan));
    }

    private String getProductDescription(final String productEan) {
        return "Description of " + productEan;
    }
}