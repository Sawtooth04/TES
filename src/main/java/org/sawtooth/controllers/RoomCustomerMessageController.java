package org.sawtooth.controllers;

import org.sawtooth.models.roomcustomer.RoomCustomer;
import org.sawtooth.models.roomcustomermessage.RoomMemberMessageUploadModel;
import org.sawtooth.models.roomcustomerpost.RoomCustomerPost;
import org.sawtooth.models.roomcustomerpost.RoomCustomerPostUploadModel;
import org.sawtooth.storage.abstractions.IStorage;
import org.sawtooth.storage.repositories.customer.abstractions.ICustomerRepository;
import org.sawtooth.storage.repositories.roomcustomer.abstractions.IRoomCustomerRepository;
import org.sawtooth.storage.repositories.roomcustomermessage.abstractions.IRoomCustomerMessageRepository;
import org.sawtooth.storage.repositories.roomcustomerpost.abstractions.IRoomCustomerPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
@RequestMapping("/room-customer-message")
public class RoomCustomerMessageController {
    private final IStorage storage;

    @Autowired
    public RoomCustomerMessageController(IStorage storage) {
        this.storage = storage;
    }

    @PostMapping("/add-member")
    public void AddMember(@RequestBody RoomMemberMessageUploadModel message) {
        try {
            int customerID = storage.GetRepository(ICustomerRepository.class).Get(SecurityContextHolder.getContext()
                .getAuthentication().getName()).customerID();
            RoomCustomer roomCustomer = storage.GetRepository(IRoomCustomerRepository.class).Get(message.roomID(), customerID);

            storage.GetRepository(IRoomCustomerMessageRepository.class).AddMemberMessage(roomCustomer.roomCustomerID(),
                message.roomTaskID(), message.text());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
