package org.sawtooth.storage.realizations;

import org.sawtooth.storage.abstractions.IStorage;
import org.sawtooth.storage.repositories.IRepository;
import org.sawtooth.storage.repositories.customer.abstractions.ICustomerRepository;
import org.sawtooth.storage.repositories.customer.realizations.CustomerRepository;
import org.sawtooth.storage.repositories.role.abstractions.IRoleRepository;
import org.sawtooth.storage.repositories.role.realizations.RoleRepository;
import org.sawtooth.storage.repositories.room.abstractions.IRoomRepository;
import org.sawtooth.storage.repositories.room.realizations.RoomRepository;
import org.sawtooth.storage.repositories.roomcustomer.abstractions.IRoomCustomerRepository;
import org.sawtooth.storage.repositories.roomcustomer.realizations.RoomCustomerRepository;
import org.sawtooth.storage.repositories.roomsolution.abstractions.IRoomSolutionRepository;
import org.sawtooth.storage.repositories.roomsolution.realizations.RoomSolutionRepository;
import org.sawtooth.storage.repositories.roomtask.abstractions.IRoomTaskRepository;
import org.sawtooth.storage.repositories.roomtask.realizations.RoomTaskRepository;
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
        repositories.put(IRoleRepository.class.getName(), RoleRepository.class.getName());
        repositories.put(IRoomRepository.class.getName(), RoomRepository.class.getName());
        repositories.put(IRoomCustomerRepository.class.getName(), RoomCustomerRepository.class.getName());
        repositories.put(IRoomTaskRepository.class.getName(), RoomTaskRepository.class.getName());
        repositories.put(IRoomSolutionRepository.class.getName(), RoomSolutionRepository.class.getName());
    }

    public <T extends IRepository> T GetRepository(Class<T> interfaceObject) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException {
        String repositoryName = repositories.get(interfaceObject.getName());
        T repository = (T) Class.forName(repositoryName).newInstance();
        repository.SetJbdcTemplate(template);
        return repository;
    }
}
