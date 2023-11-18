package org.sawtooth.controllers;

import org.sawtooth.models.roomcustomer.RoomCustomer;
import org.sawtooth.models.roomcustomermessage.*;
import org.sawtooth.models.roomsolution.RoomSolution;
import org.sawtooth.models.roomtask.RoomTask;
import org.sawtooth.storage.abstractions.IStorage;
import org.sawtooth.storage.repositories.customer.abstractions.ICustomerRepository;
import org.sawtooth.storage.repositories.roomcustomer.abstractions.IRoomCustomerRepository;
import org.sawtooth.storage.repositories.roomcustomermessage.abstractions.IRoomCustomerMessageRepository;
import org.sawtooth.storage.repositories.roomsolution.abstractions.IRoomSolutionRepository;
import org.sawtooth.storage.repositories.roomtask.abstractions.IRoomTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/add-teacher")
    public void AddTeacher(@RequestBody RoomTeacherMessageUploadModel message) {
        try {
            int customerID = storage.GetRepository(ICustomerRepository.class).Get(SecurityContextHolder.getContext()
                    .getAuthentication().getName()).customerID();
            RoomCustomer roomCustomer = storage.GetRepository(IRoomCustomerRepository.class).Get(message.roomID(), customerID);

            storage.GetRepository(IRoomCustomerMessageRepository.class).AddTeacherMessage(roomCustomer.roomCustomerID(),
                    message.roomTaskID(), message.text(), message.recipient());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @PostMapping("/add-teacher-by-room-solution")
    public void AddTeacherByRoomSolution(@RequestBody RoomTeacherMessageBySolutionUploadModel message) {
        try {
            int customerID = storage.GetRepository(ICustomerRepository.class).Get(SecurityContextHolder.getContext()
                .getAuthentication().getName()).customerID();
            RoomSolution roomSolution = storage.GetRepository(IRoomSolutionRepository.class).Get(message.roomSolutionID());
            RoomTask roomTask = storage.GetRepository(IRoomTaskRepository.class).Get(roomSolution.roomTaskID());
            RoomCustomer roomCustomer = storage.GetRepository(IRoomCustomerRepository.class).Get(roomTask.roomID(), customerID);

            storage.GetRepository(IRoomCustomerMessageRepository.class).AddTeacherMessage(roomCustomer.roomCustomerID(),
                    roomTask.roomTaskID(), message.text(), roomSolution.roomCustomerID());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @GetMapping("/get-page")
    public List<RoomCustomerMessage> GetPage(int roomTaskID, int start, int count) throws InstantiationException {
        int customerID = storage.GetRepository(ICustomerRepository.class).Get(SecurityContextHolder.getContext()
            .getAuthentication().getName()).customerID();
        RoomTask roomTask = storage.GetRepository(IRoomTaskRepository.class).Get(roomTaskID);
        RoomCustomer roomCustomer = storage.GetRepository(IRoomCustomerRepository.class).Get(roomTask.roomID(), customerID);

        return storage.GetRepository(IRoomCustomerMessageRepository.class).Get(roomTaskID, roomCustomer.roomCustomerID(),
            start, count);
    }

    @GetMapping("/get-teacher-page")
    public List<RoomCustomerMessage> GetTeacherPage(int roomCustomerID, int roomTaskID, int start, int count) throws InstantiationException {
        int customerID = storage.GetRepository(ICustomerRepository.class).Get(SecurityContextHolder.getContext()
            .getAuthentication().getName()).customerID();
        RoomTask roomTask = storage.GetRepository(IRoomTaskRepository.class).Get(roomTaskID);
        RoomCustomer roomCustomer = storage.GetRepository(IRoomCustomerRepository.class).Get(roomTask.roomID(), customerID);

        return storage.GetRepository(IRoomCustomerMessageRepository.class).GetByMember(roomTaskID,
            roomCustomer.roomCustomerID(), roomCustomerID, start, count);
    }

    @GetMapping("/get-teacher-page-by-solution")
    public List<RoomCustomerMessage> GetTeacherPage(int roomSolutionID, int start, int count) throws InstantiationException {
        int customerID = storage.GetRepository(ICustomerRepository.class).Get(SecurityContextHolder.getContext()
            .getAuthentication().getName()).customerID();
        RoomSolution roomSolution = storage.GetRepository(IRoomSolutionRepository.class).Get(roomSolutionID);
        RoomTask roomTask = storage.GetRepository(IRoomTaskRepository.class).Get(roomSolution.roomTaskID());
        RoomCustomer roomCustomer = storage.GetRepository(IRoomCustomerRepository.class).Get(roomTask.roomID(), customerID);

        return storage.GetRepository(IRoomCustomerMessageRepository.class).GetByMember(roomTask.roomTaskID(),
            roomCustomer.roomCustomerID(), roomSolution.roomCustomerID(), start, count);
    }

    @GetMapping("/get-messages-meta")
    public List<RoomCustomerMessageMeta> GetTeacherPageMeta(int roomID) throws InstantiationException {
        int customerID = storage.GetRepository(ICustomerRepository.class).Get(SecurityContextHolder.getContext()
                .getAuthentication().getName()).customerID();
        RoomCustomer roomCustomer = storage.GetRepository(IRoomCustomerRepository.class).Get(roomID, customerID);

        return storage.GetRepository(IRoomCustomerMessageRepository.class).GetMessagesMeta(roomID, roomCustomer.roomCustomerID());
    }
}
