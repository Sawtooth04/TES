package org.sawtooth.models.roomtaskvariant;

import org.springframework.web.multipart.MultipartFile;

public record RoomTaskVariantUploadModel(int roomTaskID, int variant, String description, MultipartFile file) {}
