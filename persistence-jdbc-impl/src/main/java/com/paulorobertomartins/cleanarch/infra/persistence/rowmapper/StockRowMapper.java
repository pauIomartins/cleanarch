package com.paulorobertomartins.cleanarch.infra.persistence.rowmapper;

import com.paulorobertomartins.cleanarch.core.entities.Address;
import com.paulorobertomartins.cleanarch.core.entities.Product;
import com.paulorobertomartins.cleanarch.core.entities.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@RequiredArgsConstructor
public class StockRowMapper implements RowMapper<Stock> {

    @Override
    public Stock mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Stock(rs.getLong("id"),
                new Address(rs.getLong("address_id"), rs.getString("address_label")),
                new Product(rs.getLong("product_id"), rs.getString("product_description"), rs.getString("product_ean")),
                rs.getBigDecimal("quantity"));
    }
}
