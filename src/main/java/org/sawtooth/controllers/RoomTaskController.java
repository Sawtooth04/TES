package org.sawtooth.controllers;

import org.sawtooth.models.customer.Customer;
import org.sawtooth.models.roomrole.RoomRole;
import org.sawtooth.models.roomtask.RoomTask;
import org.sawtooth.storage.abstractions.IStorage;
import org.sawtooth.storage.repositories.customer.abstractions.ICustomerRepository;
import org.sawtooth.storage.repositories.roomcustomerrole.abstractions.IRoomCustomerRoleRepository;
import org.sawtooth.storage.repositories.roomrole.abstractions.IRoomRoleRepository;
import org.sawtooth.storage.repositories.roomtask.abstractions.IRoomTaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
public class RoomTaskController {
    private final IStorage storage;

    @Autowired
    public RoomTaskController(IStorage storage) {
        this.storage = storage;
    }

    @PostMapping("/add")
    public void Add(@RequestBody RoomTask roomTask) throws InstantiationException {
        try {
            Customer customer = storage.GetRepository(ICustomerRepository.class).Get(SecurityContextHolder.getContext()
                .getAuthentication().getName());
            RoomRole teacherRole = storage.GetRepository(IRoomRoleRepository.class).Get("teacher");

            if (storage.GetRepository(IRoomCustomerRoleRepository.class).IsCustomerHasRole(roomTask.roomID(), customer, teacherRole))
                storage.GetRepository(IRoomTaskRepository.class).Add(roomTask);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
