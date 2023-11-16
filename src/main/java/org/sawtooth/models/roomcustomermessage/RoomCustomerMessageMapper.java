package org.sawtooth.models.roomcustomermessage;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomCustomerMessageMapper implements RowMapper<RoomCustomerMessage> {
    @Override
    public RoomCustomerMessage mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new RoomCustomerMessage(
            resultSet.getInt("roomCustomerMessageID"),
            resultSet.getString("name"),
            resultSet.getString("text"),
            resultSet.getTimestamp("sent"),
            resultSet.getBoolean("own"),
            resultSet.getBoolean("isRead")
        );
    }
}
