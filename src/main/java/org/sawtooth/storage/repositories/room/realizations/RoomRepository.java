package org.sawtooth.storage.repositories.room.realizations;

import org.sawtooth.models.customer.Customer;
import org.sawtooth.models.customer.CustomerMapper;
import org.sawtooth.models.room.Room;
import org.sawtooth.models.room.RoomMapper;
import org.sawtooth.models.room.RoomUpdateModel;
import org.sawtooth.storage.repositories.room.abstractions.IRoomRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import java.util.List;

public class RoomRepository implements IRoomRepository {
    private JdbcTemplate template;

    @Override
    public void SetJbdcTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Room Get(int id) {
        return template.queryForObject("SELECT * FROM get_room_by_id(?)", new RoomMapper(), id);
    }

    @Override
    public int Add(Room room) {
        return template.queryForObject("SELECT * FROM insert_room(?, ?)", new SingleColumnRowMapper<>(), room.name(),
            room.ownerID());
    }

    @Override
    public void Update(RoomUpdateModel roomUpdateModel) {
        template.queryForObject("SELECT * FROM update_room(?, ?, ?)", new SingleColumnRowMapper<>(),
            roomUpdateModel.roomID(), roomUpdateModel.name(), roomUpdateModel.description());
    }

    @Override
    public List<Room> GetCustomerRooms(int customerID) {
        return template.query("SELECT * FROM get_customer_rooms(?)", new RoomMapper(), customerID);
    }

    public Customer GetRoomOwner(int roomID) {
        return template.queryForObject("SELECT * FROM get_room_owner(?)", new CustomerMapper(), roomID);
    }
}
