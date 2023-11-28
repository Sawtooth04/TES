package org.sawtooth.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.sawtooth.models.customer.Customer;
import org.sawtooth.models.jwtroomlink.JWTRoomLinkPayload;
import org.sawtooth.models.room.Room;
import org.sawtooth.models.room.RoomBackgroundUploadModel;
import org.sawtooth.models.room.RoomUpdateModel;
import org.sawtooth.models.roomrole.RoomRole;
import org.sawtooth.storage.abstractions.IStorage;
import org.sawtooth.storage.repositories.customer.abstractions.ICustomerRepository;
import org.sawtooth.storage.repositories.room.abstractions.IRoomRepository;
import org.sawtooth.storage.repositories.roomcustomer.abstractions.IRoomCustomerRepository;
import org.sawtooth.storage.repositories.roomcustomerrole.abstractions.IRoomCustomerRoleRepository;
import org.sawtooth.storage.repositories.roomrole.abstractions.IRoomRoleRepository;
import org.sawtooth.utils.ImageHandler;
import org.sawtooth.utils.JWTBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Value("${tes.backgrounds.folder}")
    private String backgroundsFolder;
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

    @PostMapping("/upload/background")
    @ResponseBody
    public void UploadBackground(RoomBackgroundUploadModel uploadModel) {
        try {
            Customer customer = storage.GetRepository(ICustomerRepository.class).Get(SecurityContextHolder.getContext()
                .getAuthentication().getName());
            RoomRole roomRole = storage.GetRepository(IRoomRoleRepository.class).Get("teacher");
            ImageHandler imageHandler = new ImageHandler();
            Path path = Path.of(String.format("%s/", backgroundsFolder));
            String filePath = String.format("%s/%d.jpg", path, uploadModel.roomID());

            if (storage.GetRepository(IRoomCustomerRoleRepository.class).IsCustomerHasRole(uploadModel.roomID(), customer, roomRole)) {
                Files.createDirectories(path);
                storage.GetRepository(IRoomRepository.class).SetRoomColor(uploadModel.roomID(), imageHandler.GetMedianColor(uploadModel.file()));
                uploadModel.file().transferTo(new File(filePath));
                storage.GetRepository(IRoomRepository.class).SetBackgroundPath(uploadModel.roomID(), filePath);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @GetMapping(value = "/get/background", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] GetBackground(int roomID) {
        try {
            Customer customer = storage.GetRepository(ICustomerRepository.class).Get(SecurityContextHolder.getContext()
                .getAuthentication().getName());

            if (storage.GetRepository(IRoomCustomerRepository.class).IsCustomerInRoom(customer.customerID(), roomID))
                return Files.readAllBytes(Path.of(storage.GetRepository(IRoomRepository.class).GetBackgroundPath(roomID)));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @GetMapping("/get/color")
    @ResponseBody
    public String GetColor(int roomID) {
        try {
            Customer customer = storage.GetRepository(ICustomerRepository.class).Get(SecurityContextHolder.getContext()
                .getAuthentication().getName());
            Color color;

            if (storage.GetRepository(IRoomCustomerRepository.class).IsCustomerInRoom(customer.customerID(), roomID)) {
                color = new Color(storage.GetRepository(IRoomRepository.class).GetColor(roomID));
                return String.format("%d, %d, %d", color.getRed(), color.getGreen(), color.getBlue());
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
