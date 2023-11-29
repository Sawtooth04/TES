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

    @Override
    public List<Room> GetCustomerOwnRooms(int customerID) {
        return template.query("SELECT * FROM get_customer_own_rooms(?)", new RoomMapper(), customerID);
    }

    @Override
    public List<Room> GetCustomerStudyingRooms(int customerID) {
        return template.query("SELECT * FROM get_customer_studying_rooms(?)", new RoomMapper(), customerID);
    }

    public Customer GetRoomOwner(int roomID) {
        return template.queryForObject("SELECT * FROM get_room_owner(?)", new CustomerMapper(), roomID);
    }

    @Override
    public void SetRoomColor(int roomID, int color) {
        template.queryForObject("SELECT * FROM set_room_color(?, ?)", new SingleColumnRowMapper<>(), roomID, color);
    }

    @Override
    public void SetBackgroundPath(int roomID, String backgroundPath) {
        template.queryForObject("SELECT * FROM set_room_background_path(?, ?)", new SingleColumnRowMapper<>(),
            roomID, backgroundPath);
    }

    @Override
    public String GetBackgroundPath(int roomID) {
        return template.queryForObject("SELECT * FROM get_room_background_path(?)", new SingleColumnRowMapper<>(), roomID);
    }

    @Override
    public int GetColor(int roomID) {
        return template.queryForObject("SELECT * FROM get_room_color(?)", new SingleColumnRowMapper<>(), roomID);
    }
}
