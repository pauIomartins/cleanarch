package com.paulorobertomartins.cleanarch.infra.persistence;

import com.paulorobertomartins.cleanarch.core.entities.Address;
import com.paulorobertomartins.cleanarch.gateways.AddressGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.inject.Named;

@RequiredArgsConstructor
@Named
public class AddressGatewayImpl implements AddressGateway {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Address persist(Address entity) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("address").usingGeneratedKeyColumns("id");
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource("label", entity.getLabel()));

        entity.setId(key.longValue());
        return entity;
    }
}
