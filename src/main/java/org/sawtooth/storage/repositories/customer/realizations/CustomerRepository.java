package org.sawtooth.storage.repositories.customer.realizations;

import org.sawtooth.models.customer.Customer;
import org.sawtooth.models.customer.CustomerMapper;
import org.sawtooth.storage.repositories.customer.abstractions.ICustomerRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import java.util.List;

public class CustomerRepository implements ICustomerRepository {
    private JdbcTemplate template;

    @Override
    public void SetJbdcTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Customer Get(int id) {
        return template.queryForObject("SELECT * FROM get_customer_by_id(?)", new CustomerMapper(), id);
    }

    @Override
    public void Add(Customer customer) {
        template.query("SELECT * FROM insert_default_customer(?, ?, ?)", new SingleColumnRowMapper<>(),
            customer.name(), customer.passwordHash(), customer.email());
    }

    public Customer Get(String name) {
        return template.queryForObject("SELECT * FROM get_customer_by_name(?)", new CustomerMapper(), name);
    }

    @Override
    public boolean IsCustomerNameFree(String name) {
        return Boolean.TRUE.equals(template.queryForObject("SELECT * FROM is_customer_name_free(?)",
            new SingleColumnRowMapper<>(), name));
    }

    @Override
    public boolean IsCustomerEmailFree(String email) {
        return Boolean.TRUE.equals(template.queryForObject("SELECT * FROM is_customer_email_free(?)",
            new SingleColumnRowMapper<>(), email));
    }
}
