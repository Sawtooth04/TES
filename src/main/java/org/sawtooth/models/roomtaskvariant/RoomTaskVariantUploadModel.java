package org.sawtooth.models.roomtaskvariant;

import org.springframework.web.multipart.MultipartFile;

public record RoomTaskVariantUploadModel(int roomTaskID, int variant, MultipartFile file) {}
