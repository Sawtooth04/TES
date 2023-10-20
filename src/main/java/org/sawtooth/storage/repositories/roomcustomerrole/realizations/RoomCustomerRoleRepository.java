package org.sawtooth.storage.repositories.roomcustomerrole.realizations;

import org.sawtooth.models.roomcustomer.RoomCustomer;
import org.sawtooth.models.roomcustomer.RoomCustomerMapper;
import org.sawtooth.models.roomcustomerrole.RoomCustomerRole;
import org.sawtooth.storage.repositories.roomcustomerrole.abstractions.IRoomCustomerRoleRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RoomCustomerRoleRepository implements IRoomCustomerRoleRepository {
    private JdbcTemplate template;

    @Override
    public void SetJbdcTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public RoomCustomer Get(int customerID) {
        return template.queryForObject(String.format("SELECT * FROM get_room_customer_role_by_customer_id(%d)", customerID),
            new RoomCustomerMapper());
    }

    @Override
    public void Add(RoomCustomerRole roomCustomerRole) {
        template.execute(String.format("SELECT * FROM insert_room_customer_role(%d, %d)", roomCustomerRole.roomCustomerID(),
            roomCustomerRole.roomRoleID()));
    }
}
