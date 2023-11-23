package org.sawtooth.storage.repositories.roomtask.realizations;

import org.sawtooth.models.roomtask.RoomTask;
import org.sawtooth.models.roomtask.RoomTaskMapper;
import org.sawtooth.models.roomtask.RoomTaskStatistic;
import org.sawtooth.models.roomtask.RoomTaskStatisticMapper;
import org.sawtooth.storage.repositories.roomtask.abstractions.IRoomTaskRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import java.util.List;

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
    public List<RoomTask> Get(int roomID, int roomCustomerID, int start, int count) {
        return template.query("SELECT * FROM get_room_tasks(?, ?, ?, ?)", new RoomTaskMapper(), roomID, roomCustomerID,
            start, count);
    }

    @Override
    public List<RoomTask> GetLatest(int roomID, int roomCustomerID, int count) {
        return template.query("SELECT * FROM get_latest_room_tasks(?, ?, ?)", new RoomTaskMapper(), roomID,
            roomCustomerID, count);
    }

    @Override
    public List<RoomTask> GetUnverified(int roomID, int start, int count) {
        return template.query("SELECT * FROM get_tasks_with_unverified_solutions(?, ?, ?)", new RoomTaskMapper(),
            roomID, start, count);
    }

    @Override
    public List<RoomTask> GetVerified(int roomID, int start, int count) {
        return template.query("SELECT * FROM get_tasks_with_verified_solutions(?, ?, ?)", new RoomTaskMapper(),
            roomID, start, count);
    }

    @Override
    public RoomTaskStatistic GetStatistic(int roomTaskID) {
        return template.queryForObject("SELECT * FROM get_room_task_statistic(?)", new RoomTaskStatisticMapper(),
            roomTaskID);
    }

    @Override
    public void Add(RoomTask roomTask) {
        template.query("SELECT * FROM insert_room_task(?, ?, ?, ?)", new SingleColumnRowMapper<Void>(),
            roomTask.roomID(), roomTask.name(), roomTask.description(), roomTask.lastTerm());
    }
}
