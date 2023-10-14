package org.sawtooth.storage.repositories.roomtask.abstractions;

import org.sawtooth.models.roomtask.RoomTask;
import org.sawtooth.storage.repositories.IRepository;

public interface IRoomTaskRepository extends IRepository {
    public RoomTask Get(int id);

    public void Add(RoomTask roomTask);

    public int GetID(int roomID, String path);

    public String GetName(int taskID);
}
