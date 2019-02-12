package com.paulorobertomartins.cleanarch.infra.persistence;

import com.paulorobertomartins.cleanarch.core.entities.Address;
import com.paulorobertomartins.cleanarch.core.entities.Product;
import com.paulorobertomartins.cleanarch.core.entities.Stock;
import com.paulorobertomartins.cleanarch.gateways.StockGateway;
import com.paulorobertomartins.cleanarch.infra.persistence.rowmapper.StockRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.inject.Named;
import java.sql.Types;
import java.util.*;

@RequiredArgsConstructor
@Named
public class StockGatewayImpl implements StockGateway {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Stock create(final Stock stock) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("stock").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("address_id", stock.getAddress().getId());
        parameters.put("product_id", stock.getProduct().getId());
        parameters.put("quantity", stock.getQuantity());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        stock.setId(key.longValue());
        return stock;
    }

    @Override
    public Stock update(final Stock stock) {
        jdbcTemplate.update("UPDATE stock SET quantity=? WHERE id=?",
                new Object[]{stock.getQuantity(), stock.getId()},
                new int[]{Types.NUMERIC, Types.BIGINT});
        return stock;
    }

    @Override
    public Stock createOrUpdate(Stock stock) {
        if (stock.getId() == null) {
            return this.create(stock);
        } else {
            return this.update(stock);
        }
    }

    @Override
    public List<Stock> findAll() {
        return doQuery(null, null);
    }

    @Override
    public List<Stock> findByAddress(Address address) {
        return doQuery(address, null);
    }

    @Override
    public List<Stock> findByProduct(Product product) {
        return doQuery(null, product);
    }

    @Override
    public Optional<Stock> findByAddressAndProduct(final Address address, final Product product) {
        List<Stock> result = doQuery(address, product);
        if (!result.isEmpty()) {
            return Optional.of(result.get(0));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void remove(Stock stock) {
        jdbcTemplate.update("DELETE stock WHERE id=?",
                new Object[]{stock.getId()},
                new int[]{Types.BIGINT});
    }


    private List<Stock> doQuery(final Address address, final Product product) {

        final List<Object> params = new ArrayList<>();

        final StringBuilder sb = new StringBuilder();

        sb.append("SELECT s.*, \n");
        sb.append("       a.label AS address_label, \n");
        sb.append("       p.ean AS product_ean, \n");
        sb.append("       p.description AS product_description \n");
        sb.append("  FROM stock s \n");
        sb.append(" INNER JOIN address a ON (a.id = s.address_id) \n");
        sb.append(" INNER JOIN product p ON (p.id = s.product_id) \n");
        if (address != null || product != null) {

            String keyWord = " WHERE \n";

            if (address != null) {
                params.add(address.getId());
                sb.append(keyWord).append("       s.address_id = ? \n");
                keyWord = " AND ";
            }
            if (product != null) {
                params.add(product.getId());
                sb.append(keyWord).append("       s.product_id = ? \n");
            }
        }

        return jdbcTemplate.query(sb.toString(),
                params.toArray(),
                new StockRowMapper());
    }
}
