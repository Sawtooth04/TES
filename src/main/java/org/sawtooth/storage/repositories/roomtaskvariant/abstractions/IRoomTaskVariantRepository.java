package org.sawtooth.storage.repositories.roomtaskvariant.abstractions;

import org.sawtooth.models.roomtaskvariant.RoomTaskVariant;
import org.sawtooth.storage.repositories.IRepository;

public interface IRoomTaskVariantRepository extends IRepository {
    public RoomTaskVariant Get(int id);

    public RoomTaskVariant Get(int roomTaskID, int variant);

    public void Add(RoomTaskVariant roomTaskVariant);
}
