package org.sawtooth.storage.repositories.roomsolution.abstractions;

import org.sawtooth.models.roomsolution.RoomSolution;
import org.sawtooth.storage.repositories.IRepository;

public interface IRoomSolutionRepository extends IRepository {
    public RoomSolution Get(int id);

    public RoomSolution Get(int roomTaskID, int customerID);

    public void Add(int roomTaskID, int customerID, String path);

    public void SetSuccessfullyTested(int roomTaskID, int customerID);

    public boolean IsSolutionExists(int roomTaskID, int customerID);
}
