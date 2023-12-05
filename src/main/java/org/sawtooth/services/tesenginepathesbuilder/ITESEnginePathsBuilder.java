package org.sawtooth.services.tesenginepathesbuilder;

import java.nio.file.Path;

public interface ITESEnginePathsBuilder {
    public Path BuildSolutionSourcesPath(int roomID, int roomTaskID, String username);

    public Path BuildSolutionPath(int roomID, int roomTaskID, String username);

    public Path BuildCustomerTempPath(int roomID, int roomTaskID, String username);

    public Path BuildTaskVariantPath(int roomID, int roomTaskID, int variant);
}
