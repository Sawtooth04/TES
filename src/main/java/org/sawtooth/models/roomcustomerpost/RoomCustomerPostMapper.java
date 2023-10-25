package org.sawtooth.models.roomcustomerpost;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomCustomerPostMapper implements RowMapper<RoomCustomerPost> {
    @Override
    public RoomCustomerPost mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new RoomCustomerPost(
            rs.getInt("roomCustomerPostID"),
            rs.getInt("roomCustomerID"),
            rs.getTimestamp("posted"),
            rs.getString("text"),
            rs.getInt("customerID"),
            rs.getString("name")
        );
    }
}
