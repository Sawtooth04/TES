package org.sawtooth.models.roomtask;

import org.springframework.web.multipart.MultipartFile;

public record RoomTaskUploadModel (String roomID, String name, String variant, MultipartFile file) {}
