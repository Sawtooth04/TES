package org.sawtooth.controllers;

import org.sawtooth.filesvalidator.abstractions.IFilesValidator;
import org.sawtooth.models.roomtask.RoomTask;
import org.sawtooth.models.roomtask.RoomTaskUploadModel;
import org.sawtooth.models.roomtaskvariant.RoomTaskVariant;
import org.sawtooth.storage.abstractions.IStorage;
import org.sawtooth.storage.repositories.roomtask.abstractions.IRoomTaskRepository;
import org.sawtooth.storage.repositories.roomtaskvariant.abstractions.IRoomTaskVariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/task")
public class RoomTaskController {
    private final IStorage storage;
    private final IFilesValidator validator;
    @Value("${tes.tasks.folder}")
    private String tasksPath;

    @Autowired
    public RoomTaskController(IStorage storage, IFilesValidator validator) {
        this.storage = storage;
        this.validator = validator;
    }

    @PostMapping("/upload")
    public void Upload(@ModelAttribute RoomTaskUploadModel taskUploadModel) throws IOException, InstantiationException {
        if (validator.ValidateTask(taskUploadModel.file())) {
            String path = String.format("%s/%s/%s/%s", tasksPath, taskUploadModel.roomID(), taskUploadModel.name(),
                taskUploadModel.variant());
            IRoomTaskRepository roomTaskRepository = storage.GetRepository(IRoomTaskRepository.class);
            int roomID = Integer.parseInt(taskUploadModel.roomID());

            Files.createDirectories(Paths.get(path));
            taskUploadModel.file().transferTo(new File(String.format("%s/%s", path,
                taskUploadModel.file().getOriginalFilename())));
            roomTaskRepository.Add(new RoomTask(-1, roomID, taskUploadModel.name(), path));
            storage.GetRepository(IRoomTaskVariantRepository.class).Add(new RoomTaskVariant(-1,
                roomTaskRepository.GetID(roomID, path), Integer.parseInt(taskUploadModel.variant())));
        }
    }
}
