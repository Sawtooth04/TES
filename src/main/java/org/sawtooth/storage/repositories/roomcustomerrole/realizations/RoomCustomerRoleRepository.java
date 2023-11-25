package org.sawtooth.storage.repositories.roomcustomerrole.realizations;

import org.sawtooth.models.customer.Customer;
import org.sawtooth.models.customer.CustomerMapper;
import org.sawtooth.models.roomcustomer.RoomCustomer;
import org.sawtooth.models.roomcustomer.RoomCustomerMapper;
import org.sawtooth.models.roomcustomerrole.RoomCustomerRole;
import org.sawtooth.models.roomcustomerrole.RoomCustomerRoleMapper;
import org.sawtooth.models.roomrole.RoomRole;
import org.sawtooth.storage.repositories.roomcustomerrole.abstractions.IRoomCustomerRoleRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import java.util.List;

public class RoomCustomerRoleRepository implements IRoomCustomerRoleRepository {
    private JdbcTemplate template;

    @Override
    public void SetJbdcTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public RoomCustomerRole Get(int roomCustomerID) {
        return template.queryForObject("SELECT * FROM get_room_customer_role_by_customer_id(?)", new RoomCustomerRoleMapper(),
            roomCustomerID);
    }

    @Override
    public void Add(RoomCustomerRole roomCustomerRole) {
        template.query("SELECT * FROM insert_room_customer_role(?, ?)", new SingleColumnRowMapper<>(),
            roomCustomerRole.roomCustomerID(), roomCustomerRole.roomRoleID());
    }

    @Override
    public void Set(int roomCustomerID, String role) {
        template.queryForObject("SELECT * FROM set_room_customer_role(?, ?)", new SingleColumnRowMapper<>(),
            roomCustomerID, role);
    }

    @Override
    public boolean IsCustomerHasRole(int roomID, Customer customer, RoomRole role) {
        return template.queryForObject("SELECT * FROM is_customer_has_role(?, ?, ?)", new SingleColumnRowMapper<>(),
            role.roomRoleID(), customer.customerID(), roomID);
    }

    @Override
    public List<Customer> GetCustomersByRole(int roomID, int roleID) {
        return template.query("SELECT * FROM get_room_customers_by_role_id(?, ?)", new CustomerMapper(), roomID,
            roleID);
    }
}
