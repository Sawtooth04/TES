package org.sawtooth.models.roomsolution;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomVerifiedSolutionMapper implements RowMapper<RoomVerifiedSolution> {
    @Override
    public RoomVerifiedSolution mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new RoomVerifiedSolution(
            resultSet.getInt("roomSolutionID"),
            resultSet.getString("customerName")
        );
    }
}
