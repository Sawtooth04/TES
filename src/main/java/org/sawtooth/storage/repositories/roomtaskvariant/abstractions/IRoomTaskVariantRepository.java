package org.sawtooth.storage.repositories.roomtaskvariant.abstractions;

import org.sawtooth.models.roomtask.RoomTask;
import org.sawtooth.models.roomtaskvariant.RoomTaskVariant;
import org.sawtooth.storage.repositories.IRepository;

public interface IRoomTaskVariantRepository extends IRepository {
    public RoomTask Get(int id);

    public void Add(RoomTaskVariant roomTaskVariant);
}
