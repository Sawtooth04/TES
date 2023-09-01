package org.sawtooth.models.roomtask;

import org.springframework.web.multipart.MultipartFile;

public record RoomTaskUploadModel (String roomID, MultipartFile file) {}
