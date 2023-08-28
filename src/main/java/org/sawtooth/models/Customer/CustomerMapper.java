package org.sawtooth.models.Customer;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerMapper implements RowMapper<Customer> {
    @Override
    public Customer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        System.out.println(resultSet.toString());
        return new Customer(
            resultSet.getInt("customerID"),
            resultSet.getString("name"),
            resultSet.getString("passwordHash"),
            resultSet.getString("email"),
            resultSet.getInt("roleID")
        );
    }
}
