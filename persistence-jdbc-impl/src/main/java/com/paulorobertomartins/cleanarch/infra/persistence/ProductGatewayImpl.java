package com.paulorobertomartins.cleanarch.infra.persistence;

import com.paulorobertomartins.cleanarch.core.entities.Product;
import com.paulorobertomartins.cleanarch.gateways.ProductGateway;
import com.paulorobertomartins.cleanarch.infra.persistence.rowmapper.ProductRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.inject.Named;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Named
public class ProductGatewayImpl implements ProductGateway {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Product persist(Product entity) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("product").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("description", entity.getDescription());
        parameters.put("ean", entity.getEan());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        entity.setId(key.longValue());
        return entity;
    }

    @Override
    public Optional<Product> findById(long productId) {
        List<Product> result = jdbcTemplate.query("SELECT * FROM product WHERE id=?",
                new Object[]{productId},
                new ProductRowMapper());
        if (!result.isEmpty()) {
            return Optional.of(result.get(0));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Product> findByEan(String productEan) {
        List<Product> result = jdbcTemplate.query("SELECT * FROM product WHERE ean=?",
                new Object[]{productEan},
                new ProductRowMapper());
        if (!result.isEmpty()) {
            return Optional.of(result.get(0));
        } else {
            return Optional.empty();
        }
    }
}
