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
        return template.queryForObject("SELECT * FROM get_role_by_id(?)", new RoleMapper(), id);
    }

    @Override
    public void Add(Role role) {
        template.query("SELECT * FROM insert_role(?)", new RoleMapper(), role.name());
    }
}
