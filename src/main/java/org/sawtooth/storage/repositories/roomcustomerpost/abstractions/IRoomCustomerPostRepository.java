package org.sawtooth.storage.repositories.roomcustomerpost.abstractions;

import org.sawtooth.models.roomcustomer.RoomCustomer;
import org.sawtooth.models.roomcustomerpost.RoomCustomerPost;
import org.sawtooth.models.roomcustomerrole.RoomCustomerRole;
import org.sawtooth.storage.repositories.IRepository;

public interface IRoomCustomerPostRepository extends IRepository {
    public RoomCustomerPost Get(int id);

    public void Add(RoomCustomerPost roomCustomerPost);
}
