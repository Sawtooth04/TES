package org.sawtooth.models.roomtaskcomment;

import java.sql.Timestamp;

public record RoomTaskCommentView(int roomTaskCommentID, int roomTaskID, String customerName, String comment, Timestamp posted) {}
