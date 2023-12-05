package org.sawtooth.services.tesenginepathesbuilder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class TESEnginePathsBuilder implements ITESEnginePathsBuilder {
    @Value("${tes.solutions.folder}")
    private String solutionsPath;
    @Value("${tes.temp.folder}")
    private String tempPath;
    @Value("${tes.solutions.sources.folder}")
    private String sourcesFolder;
    @Value("${tes.tasks.folder}")
    private String tasksPath;

    @Override
    public Path BuildSolutionSourcesPath(int roomID, int roomTaskID, String username) {
        return Paths.get(String.format("%s/%s/%s/%s/%s", solutionsPath, roomID, roomTaskID, username, sourcesFolder));
    }

    @Override
    public Path BuildSolutionPath(int roomID, int roomTaskID, String username) {
        return Paths.get(String.format("%s/%s/%s/%s", solutionsPath, roomID, roomTaskID, username));
    }

    @Override
    public Path BuildCustomerTempPath(int roomID, int roomTaskID, String username) {
        return Paths.get(String.format("%s/%s/%s/%s", tempPath, roomID, roomTaskID, username));
    }

    @Override
    public Path BuildTaskVariantPath(int roomID, int roomTaskID, int variant) {
        return Paths.get(String.format("%s/%s/%s/%s", tasksPath, roomID, roomTaskID, variant));
    }
}
