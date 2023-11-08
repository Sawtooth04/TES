package org.sawtooth.models.roomsolution;

import org.sawtooth.models.roomtask.RoomTask;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomSolutionMapper implements RowMapper<RoomSolution> {
    @Override
    public RoomSolution mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new RoomSolution(
            resultSet.getInt("roomSolutionID"),
            resultSet.getInt("roomTaskID"),
            resultSet.getInt("roomCustomerID"),
            resultSet.getString("path"),
            resultSet.getBoolean("isSuccessfullyTested"),
            resultSet.getBoolean("isAccepted")
        );
    }
}
