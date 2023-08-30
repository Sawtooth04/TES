package org.sawtooth.storage.repositories.role.realizations;

import org.sawtooth.models.role.Role;
import org.sawtooth.models.role.RoleMapper;
import org.sawtooth.storage.repositories.role.abstractions.IRoleRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RoleRepository implements IRoleRepository {
    private JdbcTemplate template;

    @Override
    public void SetJbdcTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Role Get(int id) {
        List<Role> result = template.query(String.format("SELECT * FROM get_role_by_id(%d)", id), new RoleMapper());
        return result.get(0);
    }

    @Override
    public void Add(Role role) {
        template.execute(String.format("SELECT * FROM insert_role('%s')", role.name()));
    }
}
