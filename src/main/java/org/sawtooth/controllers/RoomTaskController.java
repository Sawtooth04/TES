package org.sawtooth.controllers;

import org.sawtooth.models.customer.Customer;
import org.sawtooth.models.roomrole.RoomRole;
import org.sawtooth.models.roomtask.RoomTask;
import org.sawtooth.storage.abstractions.IStorage;
import org.sawtooth.storage.repositories.customer.abstractions.ICustomerRepository;
import org.sawtooth.storage.repositories.roomcustomer.abstractions.IRoomCustomerRepository;
import org.sawtooth.storage.repositories.roomcustomerrole.abstractions.IRoomCustomerRoleRepository;
import org.sawtooth.storage.repositories.roomrole.abstractions.IRoomRoleRepository;
import org.sawtooth.storage.repositories.roomtask.abstractions.IRoomTaskRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class RoomTaskController {
    private final IStorage storage;

    @Autowired
    public RoomTaskController(IStorage storage) {
        this.storage = storage;
    }

    @PostMapping("/add")
    public void Add(@RequestBody RoomTask roomTask) {
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

    @GetMapping("/get")
    @ResponseBody
    public List<RoomTask> Get(int roomID, int start, int count) {
        try {
            return storage.GetRepository(IRoomTaskRepository.class).Get(roomID, start, count);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @GetMapping("/get-latest")
    @ResponseBody
    public List<RoomTask> GetLatest(int roomID) {
        try {
            Customer customer = storage.GetRepository(ICustomerRepository.class).Get(SecurityContextHolder.getContext()
                .getAuthentication().getName());

            if (storage.GetRepository(IRoomCustomerRepository.class).IsCustomerInRoom(customer.customerID(), roomID))
                return storage.GetRepository(IRoomTaskRepository.class).GetLatest(roomID, 5);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
