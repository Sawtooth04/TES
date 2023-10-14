package org.sawtooth.models.roomcustomer;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomCustomerMapper implements RowMapper<RoomCustomer> {
    public RoomCustomer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new RoomCustomer(
            resultSet.getInt("roomCustomerID"),
            resultSet.getInt("roomID"),
            resultSet.getInt("customerID"),
            resultSet.getInt("variant")
        );
    }
}
