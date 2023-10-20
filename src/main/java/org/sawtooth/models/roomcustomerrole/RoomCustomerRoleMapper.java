package org.sawtooth.models.roomcustomerrole;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomCustomerRoleMapper implements RowMapper<RoomCustomerRole> {
    @Override
    public RoomCustomerRole mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new RoomCustomerRole(
            rs.getInt("roomCustomerRoleID"),
            rs.getInt("roomCustomerID"),
            rs.getInt("roomRoleID")
        );
    }
}
