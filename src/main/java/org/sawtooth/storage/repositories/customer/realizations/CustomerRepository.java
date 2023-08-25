package org.sawtooth.storage.repositories.customer.realizations;

import org.sawtooth.models.Customer;
import org.sawtooth.storage.repositories.customer.abstractions.ICustomerRepository;
import org.springframework.jdbc.core.JdbcTemplate;

public class CustomerRepository implements ICustomerRepository {
    private JdbcTemplate template;

    @Override
    public void SetJbdcTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Customer Get(int id) {
        Customer customer = template.queryForObject(String.format("SELECT get_customer_by_id(%d)", id), Customer.class);
        return customer;
    }
}
