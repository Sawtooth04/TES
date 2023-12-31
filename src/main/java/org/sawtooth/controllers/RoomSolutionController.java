package org.sawtooth.controllers;

import org.sawtooth.models.customernotification.CustomerNotification;
import org.sawtooth.models.room.Room;
import org.sawtooth.models.roomcustomer.RoomCustomer;
import org.sawtooth.models.roomsolution.*;
import org.sawtooth.models.roomtask.RoomTask;
import org.sawtooth.models.solutiontreeitem.SolutionTreeItem;
import org.sawtooth.services.customernotificationbuilder.ICustomerNotificationBuilder;
import org.sawtooth.services.roomsolutiontreebuilder.IRoomSolutionTreeBuilder;
import org.sawtooth.services.tesenginepathesbuilder.ITESEnginePathsBuilder;
import org.sawtooth.storage.abstractions.IStorage;
import org.sawtooth.storage.repositories.customer.abstractions.ICustomerRepository;
import org.sawtooth.storage.repositories.customernotificationrepository.abstractions.ICustomerNotificationRepository;
import org.sawtooth.storage.repositories.room.abstractions.IRoomRepository;
import org.sawtooth.storage.repositories.roomcustomer.abstractions.IRoomCustomerRepository;
import org.sawtooth.storage.repositories.roomsolution.abstractions.IRoomSolutionRepository;
import org.sawtooth.storage.repositories.roomtask.abstractions.IRoomTaskRepository;
import org.sawtooth.services.customernotificationbuilder.CustomerNotificationBuilder;
import org.sawtooth.services.roomsolutiontreebuilder.RoomSolutionTreeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@RestController
@RequestMapping("/solution")
public class RoomSolutionController {
    private final IStorage storage;
    private final ICustomerNotificationBuilder customerNotificationBuilder;
    private final IRoomSolutionTreeBuilder roomSolutionTreeBuilder;
    private final ITESEnginePathsBuilder pathsBuilder;

    @Autowired
    public RoomSolutionController(IStorage storage, ICustomerNotificationBuilder customerNotificationBuilder,
        IRoomSolutionTreeBuilder roomSolutionTreeBuilder, ITESEnginePathsBuilder pathsBuilder) {
        this.storage = storage;
        this.customerNotificationBuilder = customerNotificationBuilder;
        this.roomSolutionTreeBuilder = roomSolutionTreeBuilder;
        this.pathsBuilder = pathsBuilder;
    }

    private void WriteFile(String newFilePath, ZipInputStream zipInputStream) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(newFilePath, false)) {
            fileOutputStream.write(zipInputStream.readAllBytes());
            zipInputStream.closeEntry();
        }
    }

    private String UnZip(String path, RoomSolutionUploadModel solutionUploadModel, String username) throws IOException {
        ZipEntry entry;
        Path newFilePath;
        Path rootPath = Files.createDirectories(pathsBuilder.BuildSolutionSourcesPath(solutionUploadModel.roomID(),
            solutionUploadModel.taskID(), username));

        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(path))) {
            while((entry = zipInputStream.getNextEntry()) != null) {
                newFilePath = Path.of(String.format("%s\\%s", rootPath.toString(), entry.getName()));
                if (!entry.isDirectory())
                    WriteFile(newFilePath.toString(), zipInputStream);
                else if (Files.notExists(newFilePath))
                    Files.createDirectory(newFilePath);
            }
        }
        return rootPath.toString();
    }

    private String WriteTempSolution(RoomSolutionUploadModel solutionUploadModel, String username) throws IOException {
        Path path = pathsBuilder.BuildCustomerTempPath(solutionUploadModel.roomID(), solutionUploadModel.taskID(), username);
        String filePath = String.format("%s/%s", path.toString(), solutionUploadModel.file().getOriginalFilename());

        Files.createDirectories(path);
        solutionUploadModel.file().transferTo(new File(filePath));
        return filePath;
    }

    private void DeleteTempSolution(RoomSolutionUploadModel solutionUploadModel, String username) throws IOException {
        Path path = pathsBuilder.BuildCustomerTempPath(solutionUploadModel.roomID(), solutionUploadModel.taskID(), username);
        String filePath = String.format("%s/%s", path.toString(), solutionUploadModel.file().getOriginalFilename());

        Files.deleteIfExists(Path.of(filePath));
    }

    @PostMapping("/upload")
    public void Upload(@ModelAttribute RoomSolutionUploadModel solutionUploadModel) throws IOException, InstantiationException {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        String rootPath = UnZip(WriteTempSolution(solutionUploadModel, userName), solutionUploadModel, userName);
        int customerID = storage.GetRepository(ICustomerRepository.class).Get(userName).customerID();

        DeleteTempSolution(solutionUploadModel, userName);
        storage.GetRepository(IRoomSolutionRepository.class).Add(solutionUploadModel.taskID(), customerID, rootPath);
    }

    @PostMapping("/set-successfully-tested")
    public void SetSuccessfullyTested(@RequestBody int roomTaskID) throws InstantiationException {
        int customerID = storage.GetRepository(ICustomerRepository.class).Get(SecurityContextHolder.getContext()
            .getAuthentication().getName()).customerID();

        if (storage.GetRepository(IRoomSolutionRepository.class).IsSolutionExists(roomTaskID, customerID))
            storage.GetRepository(IRoomSolutionRepository.class).SetSuccessfullyTested(roomTaskID, customerID);
    }

    @PostMapping("/set-accepted")
    public void SetAccepted(@RequestBody int roomSolutionID) throws InstantiationException {
        RoomSolution roomSolution = storage.GetRepository(IRoomSolutionRepository.class).Get(roomSolutionID);
        RoomCustomer roomCustomer = storage.GetRepository(IRoomCustomerRepository.class).Get(roomSolution.roomCustomerID());
        RoomTask roomTask = storage.GetRepository(IRoomTaskRepository.class).Get(roomSolution.roomTaskID());
        Room room = storage.GetRepository(IRoomRepository.class).Get(roomTask.roomID());
        CustomerNotification customerNotification = customerNotificationBuilder.BuildAcceptedSolutionNotification(room, roomTask);

        storage.GetRepository(IRoomSolutionRepository.class).SetAccepted(roomSolutionID);
        storage.GetRepository(ICustomerNotificationRepository.class).AddRoomCustomerNotification(roomCustomer.customerID(),
            customerNotification.header(), customerNotification.text());
    }

    @PostMapping("/set-declined")
    public void SetDeclined(@RequestBody int roomSolutionID) throws InstantiationException {
        CustomerNotificationBuilder customerNotificationBuilder = new CustomerNotificationBuilder();
        RoomSolution roomSolution = storage.GetRepository(IRoomSolutionRepository.class).Get(roomSolutionID);
        RoomCustomer roomCustomer = storage.GetRepository(IRoomCustomerRepository.class).Get(roomSolution.roomCustomerID());
        RoomTask roomTask = storage.GetRepository(IRoomTaskRepository.class).Get(roomSolution.roomTaskID());
        Room room = storage.GetRepository(IRoomRepository.class).Get(roomTask.roomID());
        CustomerNotification customerNotification = customerNotificationBuilder.BuildDeclinedSolutionNotification(room, roomTask);

        storage.GetRepository(IRoomSolutionRepository.class).SetDeclined(roomSolutionID);
        storage.GetRepository(ICustomerNotificationRepository.class).AddRoomCustomerNotification(roomCustomer.customerID(),
            customerNotification.header(), customerNotification.text());
    }

    @GetMapping("/get")
    @ResponseBody
    public RoomSolutionResponse GetSolution(int roomTaskID) throws InstantiationException {
        int customerID = storage.GetRepository(ICustomerRepository.class).Get(SecurityContextHolder.getContext()
            .getAuthentication().getName()).customerID();

        if (storage.GetRepository(IRoomSolutionRepository.class).IsSolutionExists(roomTaskID, customerID))
            return RoomSolutionResponse.FromRoomSolution(storage.GetRepository(IRoomSolutionRepository.class).Get(roomTaskID,
                customerID));
        else
            return null;
    }

    @GetMapping("/get-unverified")
    @ResponseBody
    public List<RoomUnverifiedSolution> GetUnverifiedSolutions(int roomTaskID) throws InstantiationException {
        return storage.GetRepository(IRoomSolutionRepository.class).GetUnverified(roomTaskID);
    }

    @GetMapping("/get-verified")
    @ResponseBody
    public List<RoomVerifiedSolution> GetVerifiedSolutions(int roomTaskID) throws InstantiationException {
        return storage.GetRepository(IRoomSolutionRepository.class).GetVerified(roomTaskID);
    }

    @GetMapping("/get-solution-tree-level")
    @ResponseBody
    public ArrayList<SolutionTreeItem> GetSolutionTreeLevel(int roomSolutionID, String relativePath) throws InstantiationException {
        String fullPath = storage.GetRepository(IRoomSolutionRepository.class).Get(roomSolutionID).path().concat(relativePath);
        return roomSolutionTreeBuilder.GetRoomSolutionTree(fullPath);
    }

    @GetMapping("/get-solution-tree-file")
    @ResponseBody
    public List<String> GetSolutionTreeFile(int roomSolutionID, String relativePath) throws InstantiationException, IOException {
        String fullPath = storage.GetRepository(IRoomSolutionRepository.class).Get(roomSolutionID).path().concat(relativePath);
        return Files.readAllLines(Path.of(fullPath));
    }
}
