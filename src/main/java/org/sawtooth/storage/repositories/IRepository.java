package org.sawtooth.storage.repositories;

import org.springframework.jdbc.core.JdbcTemplate;

public interface IRepository {
    public void SetJbdcTemplate(JdbcTemplate template);
}
