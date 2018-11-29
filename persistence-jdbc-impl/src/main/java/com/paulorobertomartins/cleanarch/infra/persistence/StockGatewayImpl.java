package com.paulorobertomartins.cleanarch.infra.persistence;

import com.paulorobertomartins.cleanarch.core.entities.Address;
import com.paulorobertomartins.cleanarch.core.entities.Product;
import com.paulorobertomartins.cleanarch.core.entities.Stock;
import com.paulorobertomartins.cleanarch.gateways.AddressGateway;
import com.paulorobertomartins.cleanarch.gateways.ProductGateway;
import com.paulorobertomartins.cleanarch.gateways.StockGateway;
import com.paulorobertomartins.cleanarch.infra.persistence.rowmapper.StockRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.inject.Named;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Named
public class StockGatewayImpl implements StockGateway {

    private final JdbcTemplate jdbcTemplate;
    private final AddressGateway addressGateway;
    private final ProductGateway productGateway;

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
        if (stock.getId() != null) {
            return this.create(stock);
        } else {
            return this.update(stock);
        }
    }

    @Override
    public Optional<Stock> findByAddressAndProduct(final Address address, final Product product) {
        List<Stock> result = jdbcTemplate.query("SELECT * FROM stock WHERE address_id=? AND product_id=?",
                new Object[]{address.getId(), product.getId()},
                new StockRowMapper(addressGateway, productGateway));
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
}
