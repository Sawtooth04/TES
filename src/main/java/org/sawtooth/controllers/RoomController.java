package org.sawtooth.controllers;

import org.sawtooth.models.customer.Customer;
import org.sawtooth.models.room.Room;
import org.sawtooth.models.room.RoomUpdateModel;
import org.sawtooth.models.roomrole.RoomRole;
import org.sawtooth.storage.abstractions.IStorage;
import org.sawtooth.storage.repositories.customer.abstractions.ICustomerRepository;
import org.sawtooth.storage.repositories.room.abstractions.IRoomRepository;
import org.sawtooth.storage.repositories.roomcustomer.abstractions.IRoomCustomerRepository;
import org.sawtooth.storage.repositories.roomcustomerrole.abstractions.IRoomCustomerRoleRepository;
import org.sawtooth.storage.repositories.roomrole.abstractions.IRoomRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
public class RoomController {
    private final IStorage storage;

    @Autowired
    public RoomController(IStorage storage) {
        this.storage = storage;
    }

    @GetMapping("/get-room")
    @ResponseBody
    public Room GetRoom(int roomID) {
        try {
            int customerID = storage.GetRepository(ICustomerRepository.class).Get(SecurityContextHolder.getContext()
                .getAuthentication().getName()).customerID();
            if (storage.GetRepository(IRoomCustomerRepository.class).IsCustomerInRoom(customerID, roomID))
                return storage.GetRepository(IRoomRepository.class).Get(roomID);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @GetMapping("/get-room-owner")
    @ResponseBody
    public Customer GetRoomOwner(int roomID) {
        try {
            int customerID = storage.GetRepository(ICustomerRepository.class).Get(SecurityContextHolder.getContext()
                    .getAuthentication().getName()).customerID();
            if (storage.GetRepository(IRoomCustomerRepository.class).IsCustomerInRoom(customerID, roomID))
                return storage.GetRepository(IRoomRepository.class).GetRoomOwner(roomID);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @PostMapping("/update")
    @ResponseBody
    public void UpdateRoom(@RequestBody RoomUpdateModel updateModel) {
        try {
            Customer customer = storage.GetRepository(ICustomerRepository.class).Get(SecurityContextHolder.getContext()
                .getAuthentication().getName());
            RoomRole roomRole = storage.GetRepository(IRoomRoleRepository.class).Get("teacher");
            if (storage.GetRepository(IRoomCustomerRoleRepository.class).IsCustomerHasRole(updateModel.roomID(), customer, roomRole))
                storage.GetRepository(IRoomRepository.class).Update(updateModel);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
