package org.sawtooth.storage.repositories.customer.realizations;

import org.sawtooth.models.customer.Customer;
import org.sawtooth.models.customer.CustomerMapper;
import org.sawtooth.storage.repositories.customer.abstractions.ICustomerRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CustomerRepository implements ICustomerRepository {
    private JdbcTemplate template;

    @Override
    public void SetJbdcTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Customer Get(int id) {
        return template.queryForObject(String.format("SELECT * FROM get_customer_by_id(%d)", id), new CustomerMapper());
    }

    @Override
    public void Add(Customer customer) {
        template.execute(String.format("SELECT * FROM insert_default_customer('%s', '%s', '%s')",
            customer.name(), customer.passwordHash(), customer.email()));
    }

    public Customer Get(String name) {
        return template.queryForObject(String.format("SELECT * FROM get_customer_by_name('%s')", name), new CustomerMapper());
    }
}
