package org.sawtooth.models.roomtaskcomment;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomTaskCommentMapper implements RowMapper<RoomTaskComment> {
    @Override
    public RoomTaskComment mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new RoomTaskComment(
            resultSet.getInt("roomTaskCommentID"),
            resultSet.getInt("roomTaskID"),
            resultSet.getInt("roomCustomerID"),
            resultSet.getString("comment"),
            resultSet.getTimestamp("posted")
        );
    }
}
