package org.sawtooth.models.roomtask;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomTaskStatisticMapper implements RowMapper<RoomTaskStatistic> {
    @Override
    public RoomTaskStatistic mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new RoomTaskStatistic(
            resultSet.getInt("totalCount"),
            resultSet.getInt("testedCount"),
            resultSet.getInt("acceptedCount")
        );
    }
}
