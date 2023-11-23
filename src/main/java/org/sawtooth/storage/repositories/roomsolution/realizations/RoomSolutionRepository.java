package org.sawtooth.storage.repositories.roomsolution.realizations;

import org.sawtooth.models.roomsolution.*;
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
    public RoomSolution Get(int roomTaskID, int customerID) {
        return template.queryForObject("SELECT * FROM get_room_solution(?, ?)", new RoomSolutionMapper(), roomTaskID,
            customerID);
    }

    @Override
    public List<RoomUnverifiedSolution> GetUnverified(int roomTaskID) {
        return template.query("SELECT * FROM get_unverified_solutions_by_task_id(?)", new RoomUnverifiedSolutionMapper(),
            roomTaskID);
    }

    @Override
    public List<RoomVerifiedSolution> GetVerified(int roomTaskID) {
        return template.query("SELECT * FROM get_verified_solutions_by_task_id(?)", new RoomVerifiedSolutionMapper(),
            roomTaskID);
    }

    @Override
    public void Add(int roomTaskID, int customerID, String path) {
        template.query("SELECT * FROM insert_room_solution(?, ?, ?)", new SingleColumnRowMapper<>(),
            roomTaskID, customerID, path);
    }

    @Override
    public void SetSuccessfullyTested(int roomTaskID, int customerID) {
        template.query("SELECT * FROM set_solution_successfully_tested(?, ?)", new SingleColumnRowMapper<>(),
            roomTaskID, customerID);
    }

    @Override
    public void SetAccepted(int roomSolutionID) {
        template.query("SELECT * FROM set_room_solution_accepted(?)", new SingleColumnRowMapper<>(), roomSolutionID);
    }

    @Override
    public void SetDeclined(int roomSolutionID) {
        template.query("SELECT * FROM set_room_solution_declined(?)", new SingleColumnRowMapper<>(), roomSolutionID);
    }

    @Override
    public boolean IsSolutionExists(int roomTaskID, int customerID) {
        return Boolean.TRUE.equals(template.queryForObject("SELECT * FROM is_solution_exists(?, ?)",
            new SingleColumnRowMapper<Boolean>(), roomTaskID, customerID));
    }
}
