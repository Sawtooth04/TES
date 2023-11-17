package org.sawtooth.models.roomcustomermessage;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomCustomerMessageMetaMapper implements RowMapper<RoomCustomerMessageMeta> {
    @Override
    public RoomCustomerMessageMeta mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new RoomCustomerMessageMeta(
            resultSet.getInt("roomCustomerID"),
            resultSet.getInt("roomTaskID"),
            resultSet.getString("name"),
            resultSet.getBoolean("isRead")
        );
    }
}
