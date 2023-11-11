package org.sawtooth.models.roomsolution;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomUnverifiedSolutionMapper implements RowMapper<RoomUnverifiedSolution> {
    @Override
    public RoomUnverifiedSolution mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new RoomUnverifiedSolution(
            resultSet.getInt("roomSolutionID"),
            resultSet.getString("customerName")
        );
    }
}
