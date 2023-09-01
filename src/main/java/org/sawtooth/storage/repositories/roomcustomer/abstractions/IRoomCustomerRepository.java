package org.sawtooth.storage.repositories.roomcustomer.abstractions;

import org.sawtooth.models.roomcustomer.RoomCustomer;
import org.sawtooth.storage.repositories.IRepository;

public interface IRoomCustomerRepository extends IRepository {
    public RoomCustomer Get(int id);

    public void Add(RoomCustomer roomCustomer);
}
