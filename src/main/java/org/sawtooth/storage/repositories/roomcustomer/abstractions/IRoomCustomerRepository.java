package org.sawtooth.storage.repositories.roomcustomer.abstractions;

import org.sawtooth.models.customer.Customer;
import org.sawtooth.models.roomcustomer.RoomCustomer;
import org.sawtooth.storage.repositories.IRepository;

public interface IRoomCustomerRepository extends IRepository {
    public RoomCustomer Get(int id);

    public RoomCustomer Get(int roomID, int customerID);

    public int Add(int roomID, int customerID);

    public int GetVariant(String name, int roomID);

    public boolean IsCustomerInRoom(int customerID, int roomID);
}
