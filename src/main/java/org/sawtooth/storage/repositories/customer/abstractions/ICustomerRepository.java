package org.sawtooth.storage.repositories.customer.abstractions;

import org.sawtooth.models.customer.Customer;
import org.sawtooth.storage.repositories.IRepository;

public interface ICustomerRepository extends IRepository {
    public Customer Get(int id);

    public void Add(Customer customer);

    public Customer Get(String name);
}
