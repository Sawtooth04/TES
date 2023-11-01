package org.sawtooth.models.roomtaskcomment;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomTaskCommentViewMapper implements RowMapper<RoomTaskCommentView> {
    @Override
    public RoomTaskCommentView mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new RoomTaskCommentView(
            resultSet.getInt("roomTaskCommentID"),
            resultSet.getInt("roomTaskID"),
            resultSet.getString("customerName"),
            resultSet.getString("comment"),
            resultSet.getTimestamp("posted")
        );
    }
}
