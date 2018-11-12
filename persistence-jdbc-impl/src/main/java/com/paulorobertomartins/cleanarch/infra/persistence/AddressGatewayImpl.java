package com.paulorobertomartins.cleanarch.infra.persistence;

import com.paulorobertomartins.cleanarch.core.entities.Address;
import com.paulorobertomartins.cleanarch.gateways.AddressGateway;
import com.paulorobertomartins.cleanarch.infra.persistence.rowmapper.AddressRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.inject.Named;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Address> findById(long addressId) {
        List<Address> result = jdbcTemplate.query("SELECT * FROM address WHERE id=?",
                new Object[]{addressId},
                new AddressRowMapper());
        if (!result.isEmpty()) {
            return Optional.of(result.get(0));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Address> findByLabel(String addressLabel) {
        List<Address> result = jdbcTemplate.query("SELECT * FROM address WHERE label=?",
                new Object[]{addressLabel},
                new AddressRowMapper());
        if (!result.isEmpty()) {
            return Optional.of(result.get(0));
        } else {
            return Optional.empty();
        }
    }
}
