package org.sawtooth.storage.repositories.roomcustomermessage.realizations;

import org.sawtooth.models.roomcustomermessage.RoomCustomerMessage;
import org.sawtooth.models.roomcustomermessage.RoomCustomerMessageMapper;
import org.sawtooth.models.roomcustomermessage.RoomCustomerMessageMeta;
import org.sawtooth.models.roomcustomermessage.RoomCustomerMessageMetaMapper;
import org.sawtooth.storage.repositories.roomcustomermessage.abstractions.IRoomCustomerMessageRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import java.util.List;

public class RoomCustomerMessageRepository implements IRoomCustomerMessageRepository {
    private JdbcTemplate template;

    @Override
    public void SetJbdcTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<RoomCustomerMessage> Get(int roomTaskID, int roomCustomerID, int start, int count) {
        return template.query("SELECT * FROM get_room_customer_messages(?, ?, ?, ?)", new RoomCustomerMessageMapper(),
            roomTaskID, roomCustomerID, start, count);
    }

    @Override
    public List<RoomCustomerMessage> GetByMember(int roomTaskID, int roomCustomerID, int memberID, int start, int count) {
        return template.query("SELECT * FROM get_room_customer_messages_by_member_id(?, ?, ?, ?, ?)", new RoomCustomerMessageMapper(),
                roomTaskID, roomCustomerID, memberID, start, count);
    }

    @Override
    public List<RoomCustomerMessageMeta> GetMessagesMeta(int roomID, int roomCustomerID) {
        return template.query("SELECT * FROM get_room_messages_grouped_by_name(?, ?)", new RoomCustomerMessageMetaMapper(),
            roomID, roomCustomerID);
    }

    @Override
    public void AddMemberMessage(int roomCustomerID, int roomTaskID, String text) {
        template.queryForObject("SELECT * FROM insert_room_member_message(?, ?, ?)", new SingleColumnRowMapper<>(),
            roomCustomerID, roomTaskID, text);
    }

    @Override
    public void AddTeacherMessage(int roomCustomerID, int roomTaskID, String text, int recipient) {
        template.queryForObject("SELECT * FROM insert_room_teacher_message(?, ?, ?, ?)", new SingleColumnRowMapper<>(),
                roomCustomerID, roomTaskID, text, recipient);
    }
}
