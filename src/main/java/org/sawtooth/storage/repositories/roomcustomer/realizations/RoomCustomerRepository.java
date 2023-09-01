package org.sawtooth.storage.repositories.roomcustomer.realizations;

import org.sawtooth.models.roomcustomer.RoomCustomer;
import org.sawtooth.models.roomcustomer.RoomCustomerMapper;
import org.sawtooth.storage.repositories.roomcustomer.abstractions.IRoomCustomerRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RoomCustomerRepository implements IRoomCustomerRepository {
    private JdbcTemplate template;

    @Override
    public void SetJbdcTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public RoomCustomer Get(int id) {
        List<RoomCustomer> result = template.query(String.format("SELECT * FROM get_room_customer_by_id(%d)", id),
            new RoomCustomerMapper());
        return result.get(0);
    }

    @Override
    public void Add(RoomCustomer roomCustomer) {
        template.execute(String.format("SELECT * FROM insert_room_customer(%d, %d, %d)",
            roomCustomer.roomCustomerID(), roomCustomer.roomID(), roomCustomer.customerID()));
    }
}
