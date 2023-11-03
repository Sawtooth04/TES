package org.sawtooth.controllers;

import org.sawtooth.models.roomtask.RoomTask;
import org.sawtooth.models.roomtaskcomment.RoomTaskComment;
import org.sawtooth.models.roomtaskcomment.RoomTaskCommentView;
import org.sawtooth.storage.abstractions.IStorage;
import org.sawtooth.storage.repositories.customer.abstractions.ICustomerRepository;
import org.sawtooth.storage.repositories.roomcustomer.abstractions.IRoomCustomerRepository;
import org.sawtooth.storage.repositories.roomtask.abstractions.IRoomTaskRepository;
import org.sawtooth.storage.repositories.roomtaskcomment.abstractions.IRoomTaskCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task-comment")
public class RoomTaskCommentController {
    private final IStorage storage;

    @Autowired
    public RoomTaskCommentController(IStorage storage) {
        this.storage = storage;
    }

    @GetMapping("/get-page")
    @ResponseBody
    public List<RoomTaskCommentView> Get(int roomTaskID, int start, int count) {
        try {
            return storage.GetRepository(IRoomTaskCommentRepository.class).Get(roomTaskID, start, count);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @PostMapping("/add")
    public void Add(@RequestBody RoomTaskComment roomTaskComment) {
        try {
            int customerID = storage.GetRepository(ICustomerRepository.class).Get(
                SecurityContextHolder.getContext().getAuthentication().getName()).customerID();
            RoomTask roomTask = storage.GetRepository(IRoomTaskRepository.class).Get(roomTaskComment.roomTaskID());

            if (storage.GetRepository(IRoomCustomerRepository.class).IsCustomerInRoom(customerID, roomTask.roomID()))
                storage.GetRepository(IRoomTaskCommentRepository.class).Add(roomTaskComment.WithRoomCustomerId(customerID));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @PostMapping("/delete")
    public void Delete(@RequestBody RoomTaskCommentView roomTaskComment) {
        try {
            int customerID = storage.GetRepository(ICustomerRepository.class).Get(
                    SecurityContextHolder.getContext().getAuthentication().getName()).customerID();
            RoomTask roomTask = storage.GetRepository(IRoomTaskRepository.class).Get(roomTaskComment.roomTaskID());

            if (storage.GetRepository(IRoomCustomerRepository.class).IsCustomerInRoom(customerID, roomTask.roomID()))
                storage.GetRepository(IRoomTaskCommentRepository.class).Delete(roomTaskComment.roomTaskCommentID());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
