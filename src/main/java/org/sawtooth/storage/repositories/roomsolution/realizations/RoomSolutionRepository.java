package org.sawtooth.storage.repositories.roomsolution.realizations;

import org.sawtooth.models.roomsolution.RoomSolution;
import org.sawtooth.models.roomsolution.RoomSolutionMapper;
import org.sawtooth.storage.repositories.roomsolution.abstractions.IRoomSolutionRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RoomSolutionRepository implements IRoomSolutionRepository {
    private JdbcTemplate template;

    @Override
    public void SetJbdcTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public RoomSolution Get(int id) {
        List<RoomSolution> result = template.query(String.format("SELECT * FROM get_room_solution_by_id(%d)", id),
            new RoomSolutionMapper());
        return result.get(0);
    }

    @Override
    public void Add(RoomSolution roomSolution) {
        template.execute(String.format("SELECT * FROM insert_room_solution(%d, '%s')", roomSolution.roomID(), roomSolution.path()));
    }
}
