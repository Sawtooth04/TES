package org.sawtooth.storage.repositories.customer.realizations;

import org.sawtooth.models.Customer.Customer;
import org.sawtooth.models.Customer.CustomerMapper;
import org.sawtooth.storage.repositories.customer.abstractions.ICustomerRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

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
}
