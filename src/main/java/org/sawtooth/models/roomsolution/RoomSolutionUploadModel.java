package org.sawtooth.models.roomsolution;

import org.springframework.web.multipart.MultipartFile;

public record RoomSolutionUploadModel (int roomID, int taskID, MultipartFile file) {}
