package org.sawtooth.models.roomtaskvariant;

import org.sawtooth.models.roomtask.RoomTask;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomTaskVariantMapper implements RowMapper<RoomTaskVariant> {
    @Override
    public RoomTaskVariant mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new RoomTaskVariant(
            resultSet.getInt("roomTaskVariantID"),
            resultSet.getInt("roomTaskID"),
            resultSet.getInt("variant"),
            resultSet.getString("path")
        );
    }
}
