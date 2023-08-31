package org.sawtooth.models.roomtask;

import org.sawtooth.models.roomcustomer.RoomCustomer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomTaskMapper implements RowMapper<RoomTask> {
    @Override
    public RoomTask mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new RoomTask(
                resultSet.getInt("roomTaskID"),
                resultSet.getInt("roomID"),
                resultSet.getString("path")
        );
    }
}
