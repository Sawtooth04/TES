package org.sawtooth.storage.repositories.roomtask.realizations;

import org.sawtooth.models.roomtask.RoomTask;
import org.sawtooth.models.roomtask.RoomTaskMapper;
import org.sawtooth.storage.repositories.roomtask.abstractions.IRoomTaskRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RoomTaskRepository implements IRoomTaskRepository {
    private JdbcTemplate template;

    @Override
    public void SetJbdcTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public RoomTask Get(int id) {
        List<RoomTask> result = template.query(String.format("SELECT * FROM get_room_task_by_id(%d)", id),
            new RoomTaskMapper());
        return result.get(0);
    }

    @Override
    public void Add(RoomTask roomTask) {
        template.execute(String.format("SELECT * FROM insert_room_task(%d, '%s')", roomTask.roomID(), roomTask.path()));
    }
}
