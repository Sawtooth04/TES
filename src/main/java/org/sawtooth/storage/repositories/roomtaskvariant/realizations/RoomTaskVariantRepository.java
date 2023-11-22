package org.sawtooth.storage.repositories.roomtaskvariant.realizations;

import org.sawtooth.models.roomtaskvariant.RoomTaskVariant;
import org.sawtooth.models.roomtaskvariant.RoomTaskVariantMapper;
import org.sawtooth.storage.repositories.roomtaskvariant.abstractions.IRoomTaskVariantRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import java.util.List;

public class RoomTaskVariantRepository implements IRoomTaskVariantRepository {
    private JdbcTemplate template;

    @Override
    public void SetJbdcTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public RoomTaskVariant Get(int id) {
        return template.queryForObject("SELECT * FROM get_room_task_variant_by_id(?)", new RoomTaskVariantMapper(), id);
    }

    @Override
    public RoomTaskVariant Get(int roomTaskID, int variant) {
        return template.queryForObject("SELECT * FROM get_room_task_variant(?, ?)", new RoomTaskVariantMapper(),
            roomTaskID, variant);
    }

    @Override
    public List<RoomTaskVariant> GetVariants(int roomTaskID) {
        return template.query("SELECT * FROM get_room_task_variants(?)", new RoomTaskVariantMapper(), roomTaskID);
    }

    @Override
    public void Add(RoomTaskVariant roomTaskVariant) {
        template.query("SELECT * FROM insert_room_task_variant(?, ?, ?, ?)", new SingleColumnRowMapper<Void>(),
            roomTaskVariant.roomTaskID(), roomTaskVariant.variant(), roomTaskVariant.path(), roomTaskVariant.description());
    }

    @Override
    public void Delete(RoomTaskVariant roomTaskVariant) {
        template.query("SELECT * FROM delete_room_task_variant(?, ?)", new SingleColumnRowMapper<Void>(),
            roomTaskVariant.roomTaskID(), roomTaskVariant.variant());
    }
}
