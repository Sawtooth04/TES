package org.sawtooth.controllers;

import org.sawtooth.models.customer.Customer;
import org.sawtooth.models.roomcustomer.RoomCustomer;
import org.sawtooth.models.roomcustomerrole.RoomCustomerRole;
import org.sawtooth.storage.abstractions.IStorage;
import org.sawtooth.storage.repositories.customer.abstractions.ICustomerRepository;
import org.sawtooth.storage.repositories.roomcustomer.abstractions.IRoomCustomerRepository;
import org.sawtooth.storage.repositories.roomcustomerrole.abstractions.IRoomCustomerRoleRepository;
import org.sawtooth.storage.repositories.roomrole.abstractions.IRoomRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/room-customer-role")
public class RoomCustomerRoleController {
    private final IStorage storage;

    @Autowired
    public RoomCustomerRoleController(IStorage storage) {
        this.storage = storage;
    }

    @GetMapping("/get")
    @ResponseBody
    public String GetCustomerRole(int roomID) {
        try {
            int customerID = storage.GetRepository(ICustomerRepository.class).Get(SecurityContextHolder.getContext()
                .getAuthentication().getName()).customerID();
            RoomCustomer roomCustomer = storage.GetRepository(IRoomCustomerRepository.class).Get(roomID, customerID);
            RoomCustomerRole role = storage.GetRepository(IRoomCustomerRoleRepository.class).Get(roomCustomer.roomCustomerID());

            return storage.GetRepository(IRoomRoleRepository.class).Get(role.roomRoleID()).name();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @GetMapping("/get/teachers")
    @ResponseBody
    public List<Customer> GetTeachers(int roomID) {
        try {
            int customerID = storage.GetRepository(ICustomerRepository.class).Get(SecurityContextHolder.getContext()
                .getAuthentication().getName()).customerID(),
                roleID = storage.GetRepository(IRoomRoleRepository.class).Get("teacher").roomRoleID();

            if (storage.GetRepository(IRoomCustomerRepository.class).IsCustomerInRoom(customerID, roomID))
                return storage.GetRepository(IRoomCustomerRoleRepository.class).GetCustomersByRole(roomID, roleID);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @GetMapping("/get/members")
    @ResponseBody
    public List<Customer> GetMembers(int roomID) {
        try {
            int customerID = storage.GetRepository(ICustomerRepository.class).Get(SecurityContextHolder.getContext()
                .getAuthentication().getName()).customerID(),
                roleID = storage.GetRepository(IRoomRoleRepository.class).Get("member").roomRoleID();

            if (storage.GetRepository(IRoomCustomerRepository.class).IsCustomerInRoom(customerID, roomID))
                return storage.GetRepository(IRoomCustomerRoleRepository.class).GetCustomersByRole(roomID, roleID);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
