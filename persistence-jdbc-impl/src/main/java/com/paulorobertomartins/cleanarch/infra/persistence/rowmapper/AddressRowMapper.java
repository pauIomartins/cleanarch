package com.paulorobertomartins.cleanarch.infra.persistence.rowmapper;

import com.paulorobertomartins.cleanarch.core.entities.Address;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressRowMapper implements RowMapper<Address> {

    @Override
    public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Address(rs.getLong("id"),
                rs.getString("label"));
    }
}
