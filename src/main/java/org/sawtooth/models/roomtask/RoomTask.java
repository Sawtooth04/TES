package org.sawtooth.models.roomtask;

import java.sql.Timestamp;

public record RoomTask(int roomTaskID, int roomID, String name, String description, Timestamp added, Timestamp lastTerm) {}
