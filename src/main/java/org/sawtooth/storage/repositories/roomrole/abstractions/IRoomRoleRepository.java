package org.sawtooth.storage.repositories.roomrole.abstractions;

import org.sawtooth.models.roomrole.RoomRole;
import org.sawtooth.storage.repositories.IRepository;

public interface IRoomRoleRepository extends IRepository {
    public RoomRole Get(int id);

    public RoomRole Get(String name);
}
