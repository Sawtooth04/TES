package org.sawtooth.models.room;

public record Room (int roomID, String name, int ownerID, String description, int color) {
    public Room withOwnerID(int id) {
        return new Room(roomID(), name(), id, description, color);
    }
}
