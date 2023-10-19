package org.sawtooth.models.room;

public record Room (int roomID, String name, int ownerID) {
    public Room withOwnerID(int id) {
        return new Room(roomID(), name(), id);
    }
}
