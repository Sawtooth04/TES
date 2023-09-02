package org.sawtooth.controllers;

import org.sawtooth.filesvalidator.abstractions.IFilesValidator;
import org.sawtooth.models.roomtask.RoomTask;
import org.sawtooth.models.roomtask.RoomTaskUploadModel;
import org.sawtooth.storage.abstractions.IStorage;
import org.sawtooth.storage.repositories.roomtask.abstractions.IRoomTaskRepository;
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
    public void Upload(@ModelAttribute RoomTaskUploadModel taskUploadModel) throws IOException, ClassNotFoundException,
        InstantiationException, IllegalAccessException {
        if (validator.ValidateTask(taskUploadModel.file())) {
            String path = String.format("%s%s/%s", tasksPath, taskUploadModel.roomID(),
                taskUploadModel.file().getOriginalFilename());

            Files.createDirectories(Paths.get(String.format("%s%s/", tasksPath, taskUploadModel.roomID())));
            taskUploadModel.file().transferTo(new File(path));
            storage.GetRepository(IRoomTaskRepository.class).Add(new RoomTask(-1,
                Integer.parseInt(taskUploadModel.roomID()), path));
        }
    }
}
