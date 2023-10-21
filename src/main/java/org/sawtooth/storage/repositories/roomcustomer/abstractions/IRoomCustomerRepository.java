package org.sawtooth.storage.repositories.roomcustomer.abstractions;

import org.sawtooth.models.customer.Customer;
import org.sawtooth.models.roomcustomer.RoomCustomer;
import org.sawtooth.storage.repositories.IRepository;

public interface IRoomCustomerRepository extends IRepository {
    public RoomCustomer Get(int id);

    public int Add(RoomCustomer roomCustomer);

    public int GetVariant(String name, int roomID);

    public boolean IsCustomerInRoom(int customerID, int roomID);
}
