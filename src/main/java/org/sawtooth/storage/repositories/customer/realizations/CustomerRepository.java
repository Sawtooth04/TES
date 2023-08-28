package org.sawtooth.storage.repositories.customer.realizations;

import org.sawtooth.models.Customer.Customer;
import org.sawtooth.models.Customer.CustomerMapper;
import org.sawtooth.storage.repositories.customer.abstractions.ICustomerRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Types;
import java.util.List;

public class CustomerRepository implements ICustomerRepository {
    private JdbcTemplate template;

    @Override
    public void SetJbdcTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Customer Get(int id) {
        List<Customer> result = template.query(String.format("SELECT * FROM get_customer_by_id(%d)", id), new CustomerMapper());
        return result.get(0);
    }

    @Override
    public void Add(Customer customer) {
        template.execute(String.format("SELECT * FROM insert_default_customer('%s', '%s', '%s')",
            customer.name(), customer.passwordHash(), customer.email()));
    }
}
