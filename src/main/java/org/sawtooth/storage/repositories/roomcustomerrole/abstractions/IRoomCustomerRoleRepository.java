package org.sawtooth.storage.repositories.roomcustomerrole.abstractions;

import org.sawtooth.models.customer.Customer;
import org.sawtooth.models.roomcustomer.RoomCustomer;
import org.sawtooth.models.roomcustomerrole.RoomCustomerRole;
import org.sawtooth.models.roomrole.RoomRole;
import org.sawtooth.storage.repositories.IRepository;

import java.util.List;

public interface IRoomCustomerRoleRepository extends IRepository {
    public RoomCustomerRole Get(int roomCustomerID);

    public void Add(RoomCustomerRole roomCustomerRole);

    public void Set(int roomCustomerID, String role);

    public boolean IsCustomerHasRole(int roomID, Customer customer, RoomRole role);

    public List<Customer> GetCustomersByRole(int roomID, int roleID);
}
