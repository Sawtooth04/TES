package org.sawtooth.storage.repositories.roomsolution.abstractions;

import org.sawtooth.models.roomsolution.RoomSolution;
import org.sawtooth.storage.repositories.IRepository;

public interface IRoomSolutionRepository extends IRepository {
    public RoomSolution Get(int id);

    public void Add(RoomSolution roomSolution);
}
