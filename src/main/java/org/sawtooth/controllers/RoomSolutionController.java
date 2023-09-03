package org.sawtooth.controllers;

import org.sawtooth.models.roomsolution.RoomSolution;
import org.sawtooth.models.roomsolution.RoomSolutionUploadModel;
import org.sawtooth.models.roomtask.RoomTaskUploadModel;
import org.sawtooth.storage.abstractions.IStorage;
import org.sawtooth.storage.repositories.roomsolution.abstractions.IRoomSolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@RestController
@RequestMapping("/solution")
public class RoomSolutionController {
    private final IStorage storage;
    @Value("${tes.solutions.folder}")
    private String solutionsPath;
    @Value("${tes.temp.folder}")
    private String tempPath;

    @Autowired
    public RoomSolutionController(IStorage storage) {
        this.storage = storage;
    }

    private String UnZip(String path, String solutionName, String roomID) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(path));
        ZipEntry entry;
        FileOutputStream fileOutputStream;
        String newFilePath;
        Path rootPath = Files.createDirectories(Paths.get(String.format("%s/%s/%s", solutionsPath, roomID, solutionName)));

        while((entry = zipInputStream.getNextEntry()) != null) {
            newFilePath = String.format("%s\\%s", rootPath.toString(), entry.getName());
            if (entry.isDirectory() && Files.notExists(Path.of(newFilePath)))
                Files.createDirectory(Path.of(newFilePath));
            else {
                fileOutputStream = new FileOutputStream(newFilePath);
                fileOutputStream.write(zipInputStream.readAllBytes());
                zipInputStream.closeEntry();
                fileOutputStream.close();
            }
        }
        return rootPath.toString();
    }

    private String WriteTempSolution(RoomSolutionUploadModel solutionUploadModel) throws IOException {
        String path = String.format("%s/%s/%s", tempPath, solutionUploadModel.roomID(),
            solutionUploadModel.file().getOriginalFilename());

        Files.createDirectories(Paths.get(String.format("%s/%s", tempPath, solutionUploadModel.roomID())));
        solutionUploadModel.file().transferTo(new File(path));
        return path;
    }

    @PostMapping("/upload")
    public void Upload(@ModelAttribute RoomSolutionUploadModel solutionUploadModel) throws IOException, InstantiationException {
        String solutionName = Objects.requireNonNull(solutionUploadModel.file().getOriginalFilename()).substring(0,
            solutionUploadModel.file().getOriginalFilename().lastIndexOf('.'));
        String rootPath = UnZip(WriteTempSolution(solutionUploadModel), solutionName, solutionUploadModel.roomID());

        storage.GetRepository(IRoomSolutionRepository.class).Add(new RoomSolution(-1,
            Integer.parseInt(solutionUploadModel.roomID()), rootPath));
    }
}
