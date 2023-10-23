package org.sawtooth.controllers;

import org.sawtooth.models.roomcustomer.RoomCustomer;
import org.sawtooth.models.roomcustomerpost.RoomCustomerPost;
import org.sawtooth.models.roomcustomerpost.RoomCustomerPostUploadModel;
import org.sawtooth.storage.abstractions.IStorage;
import org.sawtooth.storage.repositories.customer.abstractions.ICustomerRepository;
import org.sawtooth.storage.repositories.roomcustomer.abstractions.IRoomCustomerRepository;
import org.sawtooth.storage.repositories.roomcustomerpost.abstractions.IRoomCustomerPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/room-post")
public class RoomCustomerPostController {
    private final IStorage storage;

    @Autowired
    public RoomCustomerPostController(IStorage storage) {
        this.storage = storage;
    }

    @PostMapping("/add")
    public void Add(@RequestBody RoomCustomerPostUploadModel post) {
        try {
            int customerID = storage.GetRepository(ICustomerRepository.class).Get(SecurityContextHolder.getContext()
                .getAuthentication().getName()).customerID();
            RoomCustomer roomCustomer = storage.GetRepository(IRoomCustomerRepository.class).Get(post.roomID(), customerID);

            storage.GetRepository(IRoomCustomerPostRepository.class).Add(new RoomCustomerPost(-1,
                roomCustomer.roomCustomerID(), new Timestamp(System.currentTimeMillis()), post.text()));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @GetMapping("/get")
    @ResponseBody
    public List<RoomCustomerPost> Get(int roomID, int start, int count) {
        try {
            return storage.GetRepository(IRoomCustomerPostRepository.class).Get(roomID, start, count);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
