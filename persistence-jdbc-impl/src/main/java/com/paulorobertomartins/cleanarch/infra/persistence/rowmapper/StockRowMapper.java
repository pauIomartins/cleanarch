package com.paulorobertomartins.cleanarch.infra.persistence.rowmapper;

import com.paulorobertomartins.cleanarch.core.entities.Stock;
import com.paulorobertomartins.cleanarch.gateways.AddressGateway;
import com.paulorobertomartins.cleanarch.gateways.ProductGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@RequiredArgsConstructor
public class StockRowMapper implements RowMapper<Stock> {

    private final AddressGateway addressGateway;
    private final ProductGateway productGateway;

    @Override
    public Stock mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Stock(rs.getLong("id"),
                addressGateway.findById(rs.getLong("address_id")).get(),
                productGateway.findById(rs.getLong("product_id")).get(),
                rs.getBigDecimal("quantity"));
    }
}
