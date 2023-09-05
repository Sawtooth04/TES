package org.sawtooth.models.roomsolution;

import org.springframework.web.multipart.MultipartFile;

public record RoomSolutionUploadModel (String roomID, MultipartFile file) {}
