package org.sawtooth.models.roomrole;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomRoleMapper implements RowMapper<RoomRole> {
    @Override
    public RoomRole mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new RoomRole(
            rs.getInt("roomRoleID"),
            rs.getString("name")
        );
    }
}
