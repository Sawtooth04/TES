package org.sawtooth.storage.repositories.roomsolution.abstractions;

import org.sawtooth.models.roomsolution.RoomSolution;
import org.sawtooth.models.roomsolution.RoomUnverifiedSolution;
import org.sawtooth.models.roomsolution.RoomVerifiedSolution;
import org.sawtooth.storage.repositories.IRepository;

import java.util.List;

public interface IRoomSolutionRepository extends IRepository {
    public RoomSolution Get(int id);

    public RoomSolution Get(int roomTaskID, int customerID);

    public List<RoomUnverifiedSolution> GetUnverified(int roomTaskID);

    public List<RoomVerifiedSolution> GetVerified(int roomTaskID);

    public void Add(int roomTaskID, int customerID, String path);

    public void SetSuccessfullyTested(int roomTaskID, int customerID);

    public void SetAccepted(int roomSolutionID);

    public void SetDeclined(int roomSolutionID);

    public boolean IsSolutionExists(int roomTaskID, int customerID);
}
