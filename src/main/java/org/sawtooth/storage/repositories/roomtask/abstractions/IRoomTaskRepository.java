package org.sawtooth.storage.repositories.roomtask.abstractions;

import org.sawtooth.models.roomtask.RoomTask;
import org.sawtooth.models.roomtask.RoomTaskStatistic;
import org.sawtooth.storage.repositories.IRepository;

import java.util.List;

public interface IRoomTaskRepository extends IRepository {
    public RoomTask Get(int id);

    public List<RoomTask> Get(int roomID, int roomCustomerID, int start, int count);

    public List<RoomTask> GetLatest(int roomID, int roomCustomerID, int count);

    public List<RoomTask> GetUnverified(int roomID, int start, int count);

    public List<RoomTask> GetVerified(int roomID, int start, int count);

    public RoomTaskStatistic GetStatistic(int roomTaskID);

    public void Add(RoomTask roomTask);
}
