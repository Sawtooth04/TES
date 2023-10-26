package org.sawtooth.storage.repositories.roomtaskvariant.abstractions;

import org.sawtooth.models.roomtaskvariant.RoomTaskVariant;
import org.sawtooth.storage.repositories.IRepository;

public interface IRoomTaskVariantRepository extends IRepository {
    public RoomTaskVariant Get(int id);

    public void Add(RoomTaskVariant roomTaskVariant);
}
