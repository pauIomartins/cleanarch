package com.paulorobertomartins.cleanarch.infra.persistence;

import com.paulorobertomartins.cleanarch.core.entities.Product;
import com.paulorobertomartins.cleanarch.gateways.ProductGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

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
}
