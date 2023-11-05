package org.sawtooth.controllers;

import org.sawtooth.models.roomsolution.RoomSolution;
import org.sawtooth.models.roomsolution.RoomSolutionUploadModel;
import org.sawtooth.storage.abstractions.IStorage;
import org.sawtooth.storage.repositories.roomsolution.abstractions.IRoomSolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    @Value("${tes.solutions.sources.folder}")
    private String sourcesFolder;

    @Autowired
    public RoomSolutionController(IStorage storage) {
        this.storage = storage;
    }

    private void WriteFile(String newFilePath, ZipInputStream zipInputStream) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(newFilePath, false)) {
            fileOutputStream.write(zipInputStream.readAllBytes());
            zipInputStream.closeEntry();
        }
    }

    private String UnZip(String path, RoomSolutionUploadModel solutionUploadModel, String userName) throws IOException {
        ZipEntry entry;
        Path newFilePath;
        Path rootPath = Files.createDirectories(Paths.get(String.format("%s/%s/%s/%s/%s", solutionsPath,
            solutionUploadModel.roomID(), solutionUploadModel.taskID(), userName, sourcesFolder)));

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

    private String WriteTempSolution(RoomSolutionUploadModel solutionUploadModel, String userName) throws IOException {
        String path = String.format("%s/%s/%s/%s", tempPath, solutionUploadModel.roomID(), solutionUploadModel.taskID(),
            userName);
        String filePath = String.format("%s/%s", path, solutionUploadModel.file().getOriginalFilename());

        Files.createDirectories(Paths.get(path));
        solutionUploadModel.file().transferTo(new File(filePath));
        return filePath;
    }

    private void DeleteTempSolution(RoomSolutionUploadModel solutionUploadModel, String userName) throws IOException {
        String path = String.format("%s/%s/%s/%s", tempPath, solutionUploadModel.roomID(), solutionUploadModel.taskID(),
                userName);
        String filePath = String.format("%s/%s", path, solutionUploadModel.file().getOriginalFilename());

        Files.deleteIfExists(Path.of(filePath));
    }

    @PostMapping("/upload")
    public void Upload(@ModelAttribute RoomSolutionUploadModel solutionUploadModel) throws IOException, InstantiationException {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        String rootPath = UnZip(WriteTempSolution(solutionUploadModel, userName), solutionUploadModel, userName);

        DeleteTempSolution(solutionUploadModel, userName);
        storage.GetRepository(IRoomSolutionRepository.class).Add(new RoomSolution(-1,
            solutionUploadModel.roomID(), rootPath));
    }
}
