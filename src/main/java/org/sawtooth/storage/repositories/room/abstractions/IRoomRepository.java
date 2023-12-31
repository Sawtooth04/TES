package org.sawtooth.storage.repositories.room.abstractions;

import org.sawtooth.models.customer.Customer;
import org.sawtooth.models.room.Room;
import org.sawtooth.models.room.RoomUpdateModel;
import org.sawtooth.storage.repositories.IRepository;

import java.util.List;

public interface IRoomRepository extends IRepository {
    public Room Get(int id);

    public int Add(Room room);

    public void Update(RoomUpdateModel roomUpdateModel);

    public List<Room> GetCustomerRooms(int customerID);

    public List<Room> GetCustomerOwnRooms(int customerID);

    public List<Room> GetCustomerStudyingRooms(int customerID);

    public Customer GetRoomOwner(int roomID);

    public void SetRoomColor(int roomID, int color);

    public void SetBackgroundPath(int roomID, String backgroundPath);

    public String GetBackgroundPath(int roomID);

    public int GetColor(int roomID);
}
