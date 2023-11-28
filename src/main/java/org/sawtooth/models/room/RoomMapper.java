package org.sawtooth.models.room;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomMapper implements RowMapper<Room> {
    public Room mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Room(
            resultSet.getInt("roomID"),
            resultSet.getString("name"),
            resultSet.getInt("ownerID"),
            resultSet.getString("description"),
            resultSet.getInt("color")
        );
    }
}
