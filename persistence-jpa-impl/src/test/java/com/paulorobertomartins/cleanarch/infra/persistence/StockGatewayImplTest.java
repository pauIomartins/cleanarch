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
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@DataJpaTest
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
    public void should_find_by_address() {

        createManyStock();

        final Address address = addressGateway.findByLabel("add-1-2-3").get();

        final List<Stock> stockList = stockGateway.findByAddress(address);

        assertNotNull(stockList);
        assertFalse(stockList.isEmpty());
        assertEquals(3, stockList.size());
    }

    @Test
    public void should_find_by_product() {

        createManyStock();

        final Product product = productGateway.findByEan("p001").get();

        final List<Stock> stockList = stockGateway.findByProduct(product);

        assertNotNull(stockList);
        assertFalse(stockList.isEmpty());
        assertEquals(3, stockList.size());
    }

    @Test
    public void should_find_all() {

        createManyStock();

        final List<Stock> stockList = stockGateway.findAll();

        assertNotNull(stockList);
        assertFalse(stockList.isEmpty());
        assertEquals(12, stockList.size());
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
        return this.createProduct("product-" + ((Double) Math.random()).intValue());
    }

    private Product createProduct(final String productEan) {
        return productGateway.persist(new Product("Description of " + productEan, productEan));
    }

    private Address createAddress() {
        return this.createAddress("address-" + ((Double) Math.random()).intValue());
    }

    private Address createAddress(final String addressLabel) {
        return addressGateway.persist(new Address(addressLabel));
    }

    private Stock createStock(final Address address, final Product product) {
        final BigDecimal quantity = BigDecimal.valueOf(Math.random());
        return stockGateway.create(new Stock(address, product, quantity));
    }

    private void createManyStock() {
        final Product p1 = createProduct("p001");
        final Product p2 = createProduct("p002");
        final Product p3 = createProduct("p003");

        createProduct();

        final Address a1 = createAddress("add-1-2-3");
        final Address a2 = createAddress("add-2-4-6");
        final Address a3 = createAddress("add-3-4-5");

        this.createStock(a1, p1);
        this.createStock(a1, p2);
        this.createStock(a1, p3);

        this.createStock(a2, p1);
        this.createStock(a2, p2);
        this.createStock(a2, p3);
        this.createStock(a2, createProduct());

        this.createStock(a3, p1);
        this.createStock(a3, p2);
        this.createStock(a3, p3);
        this.createStock(a3, createProduct());
        this.createStock(a3, createProduct());
    }
}