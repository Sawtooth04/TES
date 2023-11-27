package org.sawtooth.storage.repositories.roomcustomer.realizations;

import org.sawtooth.models.roomcustomer.RoomCustomer;
import org.sawtooth.models.roomcustomer.RoomCustomerMapper;
import org.sawtooth.storage.repositories.roomcustomer.abstractions.IRoomCustomerRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import java.util.List;

public class RoomCustomerRepository implements IRoomCustomerRepository {
    private JdbcTemplate template;

    @Override
    public void SetJbdcTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public RoomCustomer Get(int id) {
        return template.queryForObject("SELECT * FROM get_room_customer_by_id(?)", new RoomCustomerMapper(), id);
    }

    public RoomCustomer Get(int roomID, int customerID) {
        return template.queryForObject("SELECT * FROM get_room_customer(?, ?)", new RoomCustomerMapper(), roomID,
            customerID);
    }

    @Override
    public int Add(int roomID, int customerID) {
        return template.queryForObject("SELECT * FROM insert_room_customer(?, ?)", new SingleColumnRowMapper<>(),
                roomID, customerID);
    }

    @Override
    public int GetVariant(String name, int roomID) {
        return template.queryForObject("SELECT * FROM get_variant(?, ?)", new SingleColumnRowMapper<>(),
            name, roomID);
    }

    @Override
    public boolean IsCustomerInRoom(int customerID, int roomID) {
        return template.queryForObject("SELECT * FROM is_customer_in_room(?, ?)", new SingleColumnRowMapper<>(),
            customerID, roomID);
    }
}
