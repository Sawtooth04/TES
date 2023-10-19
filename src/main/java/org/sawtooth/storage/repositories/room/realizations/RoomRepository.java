package org.sawtooth.storage.repositories.room.realizations;

import org.sawtooth.models.room.Room;
import org.sawtooth.models.room.RoomMapper;
import org.sawtooth.storage.repositories.room.abstractions.IRoomRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RoomRepository implements IRoomRepository {
    private JdbcTemplate template;

    @Override
    public void SetJbdcTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Room Get(int id) {
        List<Room> result = template.query(String.format("SELECT * FROM get_room_by_id(%d)", id), new RoomMapper());
        return result.get(0);
    }

    @Override
    public int Add(Room room) {
        return template.queryForObject(String.format("SELECT * FROM insert_room('%s', %d)", room.name(), room.ownerID()),
            Integer.class);
    }

    @Override
    public List<Room> GetCustomerRooms(int customerID) {
        return template.query(String.format("SELECT * FROM get_customer_rooms(%d)", customerID), new RoomMapper());
    }
}
