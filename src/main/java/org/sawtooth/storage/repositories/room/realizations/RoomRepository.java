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
    public void Add(Room room) {
        template.execute(String.format("SELECT * FROM insert_room('%s')", room.name()));
    }

    @Override
    public List<Room> GetCustomerRooms(int customerID) {
        return template.query(String.format("SELECT * FROM get_customer_rooms(%d)", customerID), new RoomMapper());
    }
}
