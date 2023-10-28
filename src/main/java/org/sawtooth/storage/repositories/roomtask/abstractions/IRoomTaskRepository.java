package org.sawtooth.storage.repositories.roomtask.abstractions;

import org.sawtooth.models.roomtask.RoomTask;
import org.sawtooth.storage.repositories.IRepository;

import java.util.List;

public interface IRoomTaskRepository extends IRepository {
    public RoomTask Get(int id);

    public List<RoomTask> Get(int roomID, int start, int count);

    public List<RoomTask> GetLatest(int roomID, int count);

    public void Add(RoomTask roomTask);
}
