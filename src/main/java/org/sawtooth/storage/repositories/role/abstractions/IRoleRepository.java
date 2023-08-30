package org.sawtooth.storage.repositories.role.abstractions;

import org.sawtooth.models.role.Role;
import org.sawtooth.storage.repositories.IRepository;

public interface IRoleRepository extends IRepository {
    public Role Get(int id);

    public void Add(Role role);
}
