package org.sawtooth.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.sawtooth.models.customer.Customer;
import org.sawtooth.models.jwtroomlink.JWTRoomLinkPayload;
import org.sawtooth.models.room.Room;
import org.sawtooth.models.room.RoomUpdateModel;
import org.sawtooth.models.roomrole.RoomRole;
import org.sawtooth.storage.abstractions.IStorage;
import org.sawtooth.storage.repositories.customer.abstractions.ICustomerRepository;
import org.sawtooth.storage.repositories.room.abstractions.IRoomRepository;
import org.sawtooth.storage.repositories.roomcustomer.abstractions.IRoomCustomerRepository;
import org.sawtooth.storage.repositories.roomcustomerrole.abstractions.IRoomCustomerRoleRepository;
import org.sawtooth.storage.repositories.roomrole.abstractions.IRoomRoleRepository;
import org.sawtooth.utils.JWTBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Date;
import java.util.Objects;

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

    @GetMapping("/get-room-token")
    @ResponseBody
    public String GetRoomToken(int roomID) {
        try {
            JWTBuilder builder = new JWTBuilder();
            Customer customer = storage.GetRepository(ICustomerRepository.class).Get(SecurityContextHolder.getContext()
                .getAuthentication().getName());
            RoomRole roomRole = storage.GetRepository(IRoomRoleRepository.class).Get("teacher");
            if (storage.GetRepository(IRoomCustomerRoleRepository.class).IsCustomerHasRole(roomID, customer, roomRole))
                return builder.GetRoomLinkToken(roomID);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @GetMapping("/join")
    @ResponseBody
    public boolean JoinRoom(String token) {
        try {
            JWTBuilder builder = new JWTBuilder();
            Customer customer = storage.GetRepository(ICustomerRepository.class).Get(SecurityContextHolder.getContext()
                .getAuthentication().getName());
            ObjectReader reader = new ObjectMapper().readerFor(JWTRoomLinkPayload.class);
            String[] parts = token.split("\\.");
            JWTRoomLinkPayload payload = reader.readValue(Base64.getDecoder().decode(parts[1]));

            if (Objects.equals(parts[2], builder.GetRoomLinkSignature(payload.roomID(), payload.exp())) && payload.exp() > new Date().getTime()) {
                storage.GetRepository(IRoomCustomerRepository.class).Add(payload.roomID(), customer.customerID());
                return true;
            }
            return false;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
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
