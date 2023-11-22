package org.sawtooth.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sawtooth.filesvalidator.abstractions.IFilesValidator;
import org.sawtooth.launcher.configuration.models.LauncherConfiguration;
import org.sawtooth.models.roomcustomer.RoomCustomer;
import org.sawtooth.models.roomtask.RoomTask;
import org.sawtooth.models.roomtaskvariant.RoomTaskVariant;
import org.sawtooth.models.roomtaskvariant.RoomTaskVariantUploadModel;
import org.sawtooth.storage.abstractions.IStorage;
import org.sawtooth.storage.repositories.customer.abstractions.ICustomerRepository;
import org.sawtooth.storage.repositories.roomcustomer.abstractions.IRoomCustomerRepository;
import org.sawtooth.storage.repositories.roomtask.abstractions.IRoomTaskRepository;
import org.sawtooth.storage.repositories.roomtaskvariant.abstractions.IRoomTaskVariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/task-variant")
public class RoomTaskVariantController {
    private final IStorage storage;
    @Value("${tes.tasks.folder}")
    private String tasksPath;

    @Autowired
    public RoomTaskVariantController(IStorage storage) {
        this.storage = storage;
    }

    private boolean ClearDirectory(File directory) {
        boolean result = true;

        for (File file : Objects.requireNonNull(directory.listFiles()))
            result &= file.delete();
        return result;
    }

    @PostMapping("/upload-or-update")
    public void UploadOrUpdate(@RequestBody RoomTaskVariantUploadModel uploadModel) throws IOException, InstantiationException {
        RoomTask roomTask = storage.GetRepository(IRoomTaskRepository.class).Get(uploadModel.roomTaskID());
        String path = String.format("%s/%s/%s/%s", tasksPath, roomTask.roomID(), roomTask.roomTaskID(),
            uploadModel.variant());
        ObjectMapper objectMapper = new ObjectMapper();

        ClearDirectory(Files.createDirectories(Paths.get(path)).toFile());
        for (int i = 0; i < uploadModel.configs().length; i++)
            try (FileWriter writer = new FileWriter(String.format("%s/%d.json", path, i), false)) {
                writer.write(objectMapper.writeValueAsString(uploadModel.configs()[i]));
            }
        storage.GetRepository(IRoomTaskVariantRepository.class).Add(new RoomTaskVariant(-1,
            roomTask.roomTaskID(), uploadModel.variant(), path, uploadModel.description()));
    }

    @GetMapping("/delete")
    public void Delete(int roomTaskID, int variant, int roomID) throws InstantiationException {
        int customerID = storage.GetRepository(ICustomerRepository.class).Get(SecurityContextHolder.getContext()
            .getAuthentication().getName()).customerID();
        RoomTaskVariant roomTaskVariant = storage.GetRepository(IRoomTaskVariantRepository.class).Get(roomTaskID, variant);
        File directory = new File(roomTaskVariant.path());

        if (storage.GetRepository(IRoomCustomerRepository.class).IsCustomerInRoom(customerID, roomID) &&
            ClearDirectory(directory) && directory.delete())
            storage.GetRepository(IRoomTaskVariantRepository.class).Delete(roomTaskVariant);
    }

    @GetMapping("/get")
    @ResponseBody
    public RoomTaskVariant Get(int roomID, int roomTaskID) throws InstantiationException {
        int customerID = storage.GetRepository(ICustomerRepository.class).Get(SecurityContextHolder.getContext()
            .getAuthentication().getName()).customerID();
        RoomCustomer roomCustomer = storage.GetRepository(IRoomCustomerRepository.class).Get(roomID, customerID);

        if (storage.GetRepository(IRoomCustomerRepository.class).IsCustomerInRoom(customerID, roomID))
            return storage.GetRepository(IRoomTaskVariantRepository.class).Get(roomTaskID, roomCustomer.variant());
        return null;
    }

    @GetMapping("/get-all")
    @ResponseBody
    public List<RoomTaskVariant> GetAll(int roomID, int roomTaskID) throws InstantiationException {
        int customerID = storage.GetRepository(ICustomerRepository.class).Get(SecurityContextHolder.getContext()
            .getAuthentication().getName()).customerID();

        if (storage.GetRepository(IRoomCustomerRepository.class).IsCustomerInRoom(customerID, roomID))
            return storage.GetRepository(IRoomTaskVariantRepository.class).GetVariants(roomTaskID);
        return null;
    }

    @GetMapping("/get-body")
    @ResponseBody
    public ArrayList<LauncherConfiguration> GetBody(int roomID, int roomTaskID, int variant) throws InstantiationException, IOException {
        int customerID = storage.GetRepository(ICustomerRepository.class).Get(SecurityContextHolder.getContext()
            .getAuthentication().getName()).customerID();
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<LauncherConfiguration> result = new ArrayList<>();
        File root = new File(storage.GetRepository(IRoomTaskVariantRepository.class).Get(roomTaskID, variant).path());

        if (storage.GetRepository(IRoomCustomerRepository.class).IsCustomerInRoom(customerID, roomID))
            for (File file : Objects.requireNonNull(root.listFiles()))
                result.add(mapper.readValue(file, LauncherConfiguration.class));
        return result;
    }
}
