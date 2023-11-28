package org.sawtooth.models.room;

import org.springframework.web.multipart.MultipartFile;

public record RoomBackgroundUploadModel(int roomID, MultipartFile file) {
}
