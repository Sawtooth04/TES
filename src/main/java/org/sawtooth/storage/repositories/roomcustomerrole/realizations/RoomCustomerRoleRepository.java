package org.sawtooth.storage.repositories.roomcustomerrole.realizations;

import org.sawtooth.models.customer.Customer;
import org.sawtooth.models.roomcustomer.RoomCustomer;
import org.sawtooth.models.roomcustomer.RoomCustomerMapper;
import org.sawtooth.models.roomcustomerrole.RoomCustomerRole;
import org.sawtooth.models.roomrole.RoomRole;
import org.sawtooth.storage.repositories.roomcustomerrole.abstractions.IRoomCustomerRoleRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;

public class RoomCustomerRoleRepository implements IRoomCustomerRoleRepository {
    private JdbcTemplate template;

    @Override
    public void SetJbdcTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public RoomCustomer Get(int customerID) {
        return template.queryForObject("SELECT * FROM get_room_customer_role_by_customer_id(?)", new RoomCustomerMapper(),
            customerID);
    }

    @Override
    public void Add(RoomCustomerRole roomCustomerRole) {
        template.query("SELECT * FROM insert_room_customer_role(?, ?)", new SingleColumnRowMapper<>(),
            roomCustomerRole.roomCustomerID(), roomCustomerRole.roomRoleID());
    }

    @Override
    public boolean IsCustomerHasRole(int roomID, Customer customer, RoomRole role) {
        return template.queryForObject("SELECT * FROM is_customer_has_role(?, ?, ?)", new SingleColumnRowMapper<>(),
            role.roomRoleID(), customer.customerID(), roomID);
    }
}
