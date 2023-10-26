package org.sawtooth.storage.repositories.roomtask.realizations;

import org.sawtooth.models.roomtask.RoomTask;
import org.sawtooth.models.roomtask.RoomTaskMapper;
import org.sawtooth.storage.repositories.roomtask.abstractions.IRoomTaskRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;

public class RoomTaskRepository implements IRoomTaskRepository {
    private JdbcTemplate template;

    @Override
    public void SetJbdcTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public RoomTask Get(int id) {
        return template.queryForObject("SELECT * FROM get_room_task_by_id(?)", new RoomTaskMapper(), id);
    }

    @Override
    public void Add(RoomTask roomTask) {
        template.query("SELECT * FROM insert_room_task(?, ?, ?, ?)", new SingleColumnRowMapper<Void>(),
            roomTask.roomID(), roomTask.name(), roomTask.description(), roomTask.lastTerm());
    }
}
