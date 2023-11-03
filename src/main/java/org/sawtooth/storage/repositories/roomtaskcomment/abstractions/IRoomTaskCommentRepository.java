package org.sawtooth.storage.repositories.roomtaskcomment.abstractions;

import org.sawtooth.models.roomtaskcomment.RoomTaskComment;
import org.sawtooth.models.roomtaskcomment.RoomTaskCommentView;
import org.sawtooth.storage.repositories.IRepository;

import java.util.List;

public interface IRoomTaskCommentRepository extends IRepository {
    public RoomTaskComment Get(int id);

    public void Add(RoomTaskComment roomTaskComment);

    public List<RoomTaskCommentView> Get(int roomTaskID, int start, int count);

    public void Delete(int id);
}
