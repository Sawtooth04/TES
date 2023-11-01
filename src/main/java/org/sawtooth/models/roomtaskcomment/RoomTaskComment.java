package org.sawtooth.models.roomtaskcomment;

import java.sql.Timestamp;

public record RoomTaskComment(int roomTaskCommentID, int roomTaskID, int roomCustomerID, String comment, Timestamp posted) {}
