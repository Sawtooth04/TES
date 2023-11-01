package org.sawtooth.storage.repositories.roomtaskcomment.realizations;

import org.sawtooth.models.roomtask.RoomTask;
import org.sawtooth.models.roomtask.RoomTaskMapper;
import org.sawtooth.models.roomtaskcomment.RoomTaskComment;
import org.sawtooth.models.roomtaskcomment.RoomTaskCommentMapper;
import org.sawtooth.models.roomtaskcomment.RoomTaskCommentView;
import org.sawtooth.models.roomtaskcomment.RoomTaskCommentViewMapper;
import org.sawtooth.storage.repositories.roomtaskcomment.abstractions.IRoomTaskCommentRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import java.util.List;

public class RoomTaskCommentRepository implements IRoomTaskCommentRepository {
    private JdbcTemplate template;

    @Override
    public void SetJbdcTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public RoomTaskComment Get(int id) {
        return template.queryForObject("SELECT * FROM get_room_task_comment_by_id(?)", new RoomTaskCommentMapper(), id);
    }

    @Override
    public void Add(RoomTaskComment roomTaskComment) {
        template.query("SELECT * FROM insert_room_task_comment(?, ?, ?)", new SingleColumnRowMapper<Void>(),
            roomTaskComment.roomTaskID(), roomTaskComment.roomCustomerID(), roomTaskComment.comment());
    }

    @Override
    public List<RoomTaskCommentView> Get(int roomTaskID, int start, int count) {
        return template.query("SELECT * FROM get_room_task_comments(?, ?, ?)", new RoomTaskCommentViewMapper(),
            roomTaskID, start, count);
    }
}
