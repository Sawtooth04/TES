package org.sawtooth.models.role;

import org.sawtooth.models.customer.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleMapper implements RowMapper<Role> {
    public Role mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Role(
            resultSet.getInt("roleID"),
            resultSet.getString("name")
        );
    }
}
