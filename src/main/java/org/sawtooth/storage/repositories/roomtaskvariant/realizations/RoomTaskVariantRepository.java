package org.sawtooth.storage.repositories.roomtaskvariant.realizations;

import org.sawtooth.models.roomtask.RoomTask;
import org.sawtooth.models.roomtask.RoomTaskMapper;
import org.sawtooth.models.roomtaskvariant.RoomTaskVariant;
import org.sawtooth.storage.repositories.roomtaskvariant.abstractions.IRoomTaskVariantRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import java.util.List;

public class RoomTaskVariantRepository implements IRoomTaskVariantRepository {
    private JdbcTemplate template;

    @Override
    public void SetJbdcTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public RoomTask Get(int id) {
        return template.queryForObject("SELECT * FROM get_room_task_variant_by_id(?)", new RoomTaskMapper(), id);
    }

    @Override
    public void Add(RoomTaskVariant roomTaskVariant) {
        template.query("SELECT * FROM insert_room_task_variant(?, ?)", new SingleColumnRowMapper<>(),
            roomTaskVariant.roomTaskID(), roomTaskVariant.variant());
    }
}
