package org.sawtooth.storage.repositories.roomrole.realizations;

import org.sawtooth.models.roomrole.RoomRole;
import org.sawtooth.models.roomrole.RoomRoleMapper;
import org.sawtooth.storage.repositories.roomrole.abstractions.IRoomRoleRepository;
import org.springframework.jdbc.core.JdbcTemplate;

public class RoomRoleRepository implements IRoomRoleRepository {
    private JdbcTemplate template;

    @Override
    public void SetJbdcTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public RoomRole Get(int id) {
        return template.queryForObject("SELECT * FROM get_room_role_by_id(?)", new RoomRoleMapper(), id);
    }

    @Override
    public RoomRole Get(String name) {
        return template.queryForObject("SELECT * FROM get_room_role_by_name(?)", new RoomRoleMapper(), name);
    }
}
