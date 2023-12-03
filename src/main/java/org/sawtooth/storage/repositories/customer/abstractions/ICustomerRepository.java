package org.sawtooth.storage.repositories.customer.abstractions;

import org.sawtooth.models.customer.Customer;
import org.sawtooth.storage.repositories.IRepository;

public interface ICustomerRepository extends IRepository {
    public Customer Get(int customerID);

    public Integer Add(Customer customer);

    public Customer Get(String name);

    public boolean IsCustomerNameFree(String name);

    public boolean IsCustomerEmailFree(String email);

    public void SetVerified(int customerID);
}
