package org.sawtooth.models.roomcustomermessage;

public record RoomTeacherMessageUploadModel(int roomID, int roomTaskID, String text, int recipient) {
}
