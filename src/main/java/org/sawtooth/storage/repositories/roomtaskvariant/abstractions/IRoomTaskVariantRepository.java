package org.sawtooth.storage.repositories.roomtaskvariant.abstractions;

import org.sawtooth.models.roomtaskvariant.RoomTaskVariant;
import org.sawtooth.storage.repositories.IRepository;

import java.util.List;

public interface IRoomTaskVariantRepository extends IRepository {
    public RoomTaskVariant Get(int id);

    public RoomTaskVariant Get(int roomTaskID, int variant);

    public List<RoomTaskVariant> GetVariants(int roomTaskID);

    public void Add(RoomTaskVariant roomTaskVariant);
}
