package org.sawtooth.controllers;

import org.sawtooth.filesvalidator.abstractions.IFilesValidator;
import org.sawtooth.models.roomtask.RoomTaskUploadModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final IFilesValidator validator;
    @Value("${tes.task.folder}")
    private String tasksPath;

    @Autowired
    public TaskController(IFilesValidator validator) {
        this.validator = validator;
    }

    @PostMapping("/upload")
    public void upload(@ModelAttribute RoomTaskUploadModel taskUploadModel) throws IOException {
        if (validator.ValidateTask(taskUploadModel.file())) {
            Files.createDirectories(Paths.get(String.format("%s/%s/", tasksPath, taskUploadModel.roomID())));
            taskUploadModel.file().transferTo(new File(String.format("%s/%s/%s",
                tasksPath, taskUploadModel.roomID(), taskUploadModel.file().getOriginalFilename()))
            );
        }
    }
}
