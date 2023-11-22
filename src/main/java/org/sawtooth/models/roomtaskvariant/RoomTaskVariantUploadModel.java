package org.sawtooth.models.roomtaskvariant;

import org.sawtooth.launcher.configuration.models.LauncherConfiguration;

public record RoomTaskVariantUploadModel(int roomTaskID, int variant, String description,
                                         LauncherConfigurationUploadModel[] configs) {}
