package org.sawtooth.storage.repositories.roomsolution.realizations;

import org.sawtooth.models.roomsolution.RoomSolution;
import org.sawtooth.models.roomsolution.RoomSolutionMapper;
import org.sawtooth.storage.repositories.roomsolution.abstractions.IRoomSolutionRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import java.util.List;

public class RoomSolutionRepository implements IRoomSolutionRepository {
    private JdbcTemplate template;

    @Override
    public void SetJbdcTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public RoomSolution Get(int id) {
        return template.queryForObject("SELECT * FROM get_room_solution_by_id(?)", new RoomSolutionMapper(), id);
    }

    @Override
    public void Add(RoomSolution roomSolution) {
        template.query("SELECT * FROM insert_room_solution(?, ?)", new SingleColumnRowMapper<>(),
            roomSolution.roomID(), roomSolution.path());
    }
}
