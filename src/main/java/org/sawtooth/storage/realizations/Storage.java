package org.sawtooth.storage.realizations;

import org.sawtooth.storage.abstractions.IStorage;
import org.sawtooth.storage.repositories.IRepository;
import org.sawtooth.storage.repositories.customer.abstractions.ICustomerRepository;
import org.sawtooth.storage.repositories.customer.realizations.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class Storage implements IStorage {
    private static HashMap<String, String> repositories;
    private final JdbcTemplate template;

    @Autowired
    public Storage(JdbcTemplate template) {
        if (repositories == null) {
            repositories = new HashMap<>();
            Initialization();
        }
        this.template = template;
    }

    private static void Initialization() {
        repositories.put(ICustomerRepository.class.getName(), CustomerRepository.class.getName());
    }

    public <T extends IRepository> T GetRepository(Class<T> interfaceObject) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException {
        String repositoryName = repositories.get(interfaceObject.getName());
        T repository = (T) Class.forName(repositoryName).newInstance();
        repository.SetJbdcTemplate(template);
        return repository;
    }
}
