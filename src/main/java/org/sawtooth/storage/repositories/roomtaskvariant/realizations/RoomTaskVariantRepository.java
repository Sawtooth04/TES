package org.sawtooth.storage.repositories.roomtaskvariant.realizations;

import org.sawtooth.models.roomtask.RoomTask;
import org.sawtooth.models.roomtask.RoomTaskMapper;
import org.sawtooth.models.roomtaskvariant.RoomTaskVariant;
import org.sawtooth.storage.repositories.roomtaskvariant.abstractions.IRoomTaskVariantRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RoomTaskVariantRepository implements IRoomTaskVariantRepository {
    private JdbcTemplate template;

    @Override
    public void SetJbdcTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public RoomTask Get(int id) {
        List<RoomTask> result = template.query(String.format("SELECT * FROM get_room_task_variant_by_id(%d)", id),
            new RoomTaskMapper());
        return result.get(0);
    }

    @Override
    public void Add(RoomTaskVariant roomTaskVariant) {
        template.execute(String.format("SELECT * FROM insert_room_task_variant(%d, %d)", roomTaskVariant.roomTaskID(),
            roomTaskVariant.variant()));
    }
}
