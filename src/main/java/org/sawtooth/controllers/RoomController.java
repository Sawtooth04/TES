package org.sawtooth.controllers;

import org.sawtooth.models.room.Room;
import org.sawtooth.storage.abstractions.IStorage;
import org.sawtooth.storage.repositories.customer.abstractions.ICustomerRepository;
import org.sawtooth.storage.repositories.room.abstractions.IRoomRepository;
import org.sawtooth.storage.repositories.roomcustomer.abstractions.IRoomCustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
}
