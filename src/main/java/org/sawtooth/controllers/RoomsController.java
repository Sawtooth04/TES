package org.sawtooth.controllers;

import org.sawtooth.models.room.Room;
import org.sawtooth.models.roomcustomer.RoomCustomer;
import org.sawtooth.models.roomcustomerrole.RoomCustomerRole;
import org.sawtooth.storage.abstractions.IStorage;
import org.sawtooth.storage.repositories.customer.abstractions.ICustomerRepository;
import org.sawtooth.storage.repositories.room.abstractions.IRoomRepository;
import org.sawtooth.storage.repositories.roomcustomer.abstractions.IRoomCustomerRepository;
import org.sawtooth.storage.repositories.roomcustomerrole.abstractions.IRoomCustomerRoleRepository;
import org.sawtooth.storage.repositories.roomrole.abstractions.IRoomRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomsController {
    private final IStorage storage;

    @Autowired
    public RoomsController(IStorage storage) {
        this.storage = storage;
    }

    @GetMapping("/get-rooms")
    @ResponseBody
    public List<Room> GetRooms() {
        try {
            int customerID = storage.GetRepository(ICustomerRepository.class).Get(SecurityContextHolder.getContext()
                .getAuthentication().getName()).customerID();
            return storage.GetRepository(IRoomRepository.class).GetCustomerRooms(customerID);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @PostMapping("/create")
    public void CreateRoom(@RequestBody Room room) {
        try {
            int customerID = storage.GetRepository(ICustomerRepository.class).Get(SecurityContextHolder.getContext()
                .getAuthentication().getName()).customerID(),
                roomID = storage.GetRepository(IRoomRepository.class).Add(room.withOwnerID(customerID)),
                roomCustomerID = storage.GetRepository(IRoomCustomerRepository.class).Add(new RoomCustomer(-1,
                    roomID, customerID, -1)),
                roomRoleID = storage.GetRepository(IRoomRoleRepository.class).Get("teacher").roomRoleID();

            storage.GetRepository(IRoomCustomerRoleRepository.class).Add(new RoomCustomerRole(-1,
                roomCustomerID, roomRoleID));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
