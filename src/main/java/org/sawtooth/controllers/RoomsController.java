package org.sawtooth.controllers;

import org.sawtooth.models.room.Room;
import org.sawtooth.storage.abstractions.IStorage;
import org.sawtooth.storage.repositories.customer.abstractions.ICustomerRepository;
import org.sawtooth.storage.repositories.room.abstractions.IRoomRepository;
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
            storage.GetRepository(IRoomRepository.class).Add(room);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
