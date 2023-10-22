package org.sawtooth.storage.repositories.roomcustomerpost.realizations;

import org.sawtooth.models.roomcustomerpost.RoomCustomerPost;
import org.sawtooth.models.roomcustomerpost.RoomCustomerPostMapper;
import org.sawtooth.storage.repositories.roomcustomerpost.abstractions.IRoomCustomerPostRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;

public class RoomCustomerPostRepository implements IRoomCustomerPostRepository {
    private JdbcTemplate template;

    @Override
    public void SetJbdcTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public RoomCustomerPost Get(int id) {
        return template.queryForObject("SELECT * FROM get_room_customer_post_by_id(?)", new RoomCustomerPostMapper(),
            id);
    }

    @Override
    public void Add(RoomCustomerPost roomCustomerPost) {
        template.query("SELECT * FROM insert_room_customer_post(?, ?)", new SingleColumnRowMapper<>(),
            roomCustomerPost.roomCustomerID(), roomCustomerPost.text());
    }
}
