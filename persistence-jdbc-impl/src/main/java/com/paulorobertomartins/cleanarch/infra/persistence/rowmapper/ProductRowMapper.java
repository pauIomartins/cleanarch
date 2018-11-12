package com.paulorobertomartins.cleanarch.infra.persistence.rowmapper;


import com.paulorobertomartins.cleanarch.core.entities.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Product(rs.getLong("id"),
                rs.getString("description"),
                rs.getString("ean"));
    }
}
