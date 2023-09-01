package org.sawtooth.storage.repositories.room.abstractions;

import org.sawtooth.models.room.Room;
import org.sawtooth.storage.repositories.IRepository;

public interface IRoomRepository extends IRepository {
    public Room Get(int id);

    public void Add(Room room);
}
