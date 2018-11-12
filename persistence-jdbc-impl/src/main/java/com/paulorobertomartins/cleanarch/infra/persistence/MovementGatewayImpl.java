package com.paulorobertomartins.cleanarch.infra.persistence;

import com.paulorobertomartins.cleanarch.core.entities.Movement;
import com.paulorobertomartins.cleanarch.gateways.MovementGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Named
public class MovementGatewayImpl implements MovementGateway {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Movement create(Movement movement) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("movement").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        if (movement.getAddressFrom() != null) {
            parameters.put("address_id_from", movement.getAddressFrom().getId());
        }
        if (movement.getAddressTo() != null) {
            parameters.put("address_id_to", movement.getAddressTo().getId());
        }
        parameters.put("product_id", movement.getProduct().getId());
        parameters.put("quantity", movement.getQuantity());
        parameters.put("type", movement.getType().toString());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        movement.setId(key.longValue());
        return movement;
    }
}