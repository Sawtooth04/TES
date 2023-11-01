package org.sawtooth.controllers;

import org.sawtooth.models.roomtaskcomment.RoomTaskCommentView;
import org.sawtooth.storage.abstractions.IStorage;
import org.sawtooth.storage.repositories.roomtaskcomment.abstractions.IRoomTaskCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}
