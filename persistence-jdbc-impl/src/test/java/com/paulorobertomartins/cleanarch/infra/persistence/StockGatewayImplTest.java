package com.paulorobertomartins.cleanarch.infra.persistence;

import com.paulorobertomartins.cleanarch.core.entities.Address;
import com.paulorobertomartins.cleanarch.core.entities.Product;
import com.paulorobertomartins.cleanarch.core.entities.Stock;
import com.paulorobertomartins.cleanarch.gateways.AddressGateway;
import com.paulorobertomartins.cleanarch.gateways.ProductGateway;
import com.paulorobertomartins.cleanarch.gateways.StockGateway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@JdbcTest
@SpringBootTest(classes = {AddressGatewayImpl.class, ProductGatewayImpl.class, StockGatewayImpl.class})
public class StockGatewayImplTest {

    @Autowired
    private AddressGateway addressGateway;
    @Autowired
    private ProductGateway productGateway;
    @Autowired
    private StockGateway stockGateway;

    @Test
    public void should_find_by_address_and_product() {

        final Address address = createAddress();
        final Product product = createProduct();
        createStock(address, product);

        final Stock stock = stockGateway.findByAddressAndProduct(address, product).orElse(null);

        assertNotNull(stock);
        assertNotNull(stock.getId());
        assertEquals(stock.getAddress(), address);
        assertEquals(stock.getProduct(), product);
    }

    @Test
    public void should_create_stock() {

        final Address address = createAddress();
        final Product product = createProduct();
        final BigDecimal quantity = BigDecimal.valueOf(Math.random());

        final Stock stockSaved = stockGateway.create(new Stock(address, product, quantity));

        assertNotNull(stockSaved);
        assertNotNull(stockSaved.getId());
        assertEquals(stockSaved.getAddress(), address);
        assertEquals(stockSaved.getProduct(), product);
        assertEquals(stockSaved.getQuantity(), quantity);
    }

    @Test
    public void should_update_stock_quantity() {

        final Address address = createAddress();
        final Product product = createProduct();
        final Stock updateStock = createStock(address, product);

        final BigDecimal newQuantity = updateStock.getQuantity().add(updateStock.getQuantity());

        updateStock.setQuantity(newQuantity);

        final Stock stockSaved = stockGateway.update(updateStock);

        assertNotNull(stockSaved);
        assertEquals(stockSaved.getId(), updateStock.getId());
        assertEquals(stockSaved.getAddress(), address);
        assertEquals(stockSaved.getProduct(), product);
        assertEquals(stockSaved.getQuantity(), newQuantity);
    }

    @Test
    public void should_remove_stock() {

        final Address address = createAddress();
        final Product product = createProduct();
        final Stock removeStock = createStock(address, product);

        stockGateway.remove(removeStock);

        final Stock stock = stockGateway.findByAddressAndProduct(address, product).orElse(null);

        assertNull(stock);
    }

    private Product createProduct() {
        final String productEan = "product-" + ((Double) Math.random()).intValue();
        return productGateway.persist(new Product("Description of " + productEan, productEan));
    }

    private Address createAddress() {
        return addressGateway.persist(new Address("address-" + ((Double) Math.random()).intValue()));
    }

    private Stock createStock(final Address address, final Product product) {
        final BigDecimal quantity = BigDecimal.valueOf(Math.random());
        return stockGateway.create(new Stock(address, product, quantity));
    }
}