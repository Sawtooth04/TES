package org.sawtooth.storage.repositories.roomcustomerrole.abstractions;

import org.sawtooth.models.roomcustomer.RoomCustomer;
import org.sawtooth.models.roomcustomerrole.RoomCustomerRole;
import org.sawtooth.storage.repositories.IRepository;

public interface IRoomCustomerRoleRepository extends IRepository {
    public RoomCustomer Get(int customerID);

    public void Add(RoomCustomerRole roomCustomerRole);
}
