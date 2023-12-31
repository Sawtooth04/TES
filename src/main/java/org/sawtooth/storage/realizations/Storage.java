package org.sawtooth.storage.realizations;

import org.sawtooth.storage.abstractions.IStorage;
import org.sawtooth.storage.repositories.IRepository;
import org.sawtooth.storage.repositories.customer.abstractions.ICustomerRepository;
import org.sawtooth.storage.repositories.customer.realizations.CustomerRepository;
import org.sawtooth.storage.repositories.customernotificationrepository.abstractions.ICustomerNotificationRepository;
import org.sawtooth.storage.repositories.customernotificationrepository.realizations.CustomerNotificationRepository;
import org.sawtooth.storage.repositories.role.abstractions.IRoleRepository;
import org.sawtooth.storage.repositories.role.realizations.RoleRepository;
import org.sawtooth.storage.repositories.room.abstractions.IRoomRepository;
import org.sawtooth.storage.repositories.room.realizations.RoomRepository;
import org.sawtooth.storage.repositories.roomcustomer.abstractions.IRoomCustomerRepository;
import org.sawtooth.storage.repositories.roomcustomer.realizations.RoomCustomerRepository;
import org.sawtooth.storage.repositories.roomcustomermessage.abstractions.IRoomCustomerMessageRepository;
import org.sawtooth.storage.repositories.roomcustomermessage.realizations.RoomCustomerMessageRepository;
import org.sawtooth.storage.repositories.roomcustomerpost.abstractions.IRoomCustomerPostRepository;
import org.sawtooth.storage.repositories.roomcustomerpost.realizations.RoomCustomerPostRepository;
import org.sawtooth.storage.repositories.roomcustomerrole.abstractions.IRoomCustomerRoleRepository;
import org.sawtooth.storage.repositories.roomcustomerrole.realizations.RoomCustomerRoleRepository;
import org.sawtooth.storage.repositories.roomrole.abstractions.IRoomRoleRepository;
import org.sawtooth.storage.repositories.roomrole.realizations.RoomRoleRepository;
import org.sawtooth.storage.repositories.roomsolution.abstractions.IRoomSolutionRepository;
import org.sawtooth.storage.repositories.roomsolution.realizations.RoomSolutionRepository;
import org.sawtooth.storage.repositories.roomtask.abstractions.IRoomTaskRepository;
import org.sawtooth.storage.repositories.roomtask.realizations.RoomTaskRepository;
import org.sawtooth.storage.repositories.roomtaskcomment.abstractions.IRoomTaskCommentRepository;
import org.sawtooth.storage.repositories.roomtaskcomment.realizations.RoomTaskCommentRepository;
import org.sawtooth.storage.repositories.roomtaskvariant.abstractions.IRoomTaskVariantRepository;
import org.sawtooth.storage.repositories.roomtaskvariant.realizations.RoomTaskVariantRepository;
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
        repositories.put(IRoomTaskVariantRepository.class.getName(), RoomTaskVariantRepository.class.getName());
        repositories.put(IRoomRoleRepository.class.getName(), RoomRoleRepository.class.getName());
        repositories.put(IRoomCustomerRoleRepository.class.getName(), RoomCustomerRoleRepository.class.getName());
        repositories.put(IRoomCustomerPostRepository.class.getName(), RoomCustomerPostRepository.class.getName());
        repositories.put(IRoomTaskCommentRepository.class.getName(), RoomTaskCommentRepository.class.getName());
        repositories.put(IRoomCustomerMessageRepository.class.getName(), RoomCustomerMessageRepository.class.getName());
        repositories.put(ICustomerNotificationRepository.class.getName(), CustomerNotificationRepository.class.getName());
    }

    public <T extends IRepository> T GetRepository(Class<T> interfaceObject) throws InstantiationException{
        try {
            String repositoryName = repositories.get(interfaceObject.getName());
            T repository = (T) Class.forName(repositoryName).newInstance();
            repository.SetJbdcTemplate(template);
            return repository;
        }
        catch (Exception exception) {
            throw new InstantiationException(exception.getMessage());
        }
    }
}
