package org.sawtooth.storage.repositories.room.abstractions;

import org.sawtooth.models.room.Room;
import org.sawtooth.storage.repositories.IRepository;

import java.util.List;

public interface IRoomRepository extends IRepository {
    public Room Get(int id);

    public void Add(Room room);

    public List<Room> GetCustomerRooms(int customerID);
}
