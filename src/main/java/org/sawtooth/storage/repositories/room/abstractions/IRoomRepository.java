package org.sawtooth.storage.repositories.room.abstractions;

import org.sawtooth.models.customer.Customer;
import org.sawtooth.models.room.Room;
import org.sawtooth.storage.repositories.IRepository;

import java.util.List;

public interface IRoomRepository extends IRepository {
    public Room Get(int id);

    public int Add(Room room);

    public List<Room> GetCustomerRooms(int customerID);

    public Customer GetRoomOwner(int roomID);
}
