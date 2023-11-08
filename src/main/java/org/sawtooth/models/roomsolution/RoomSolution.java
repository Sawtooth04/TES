package org.sawtooth.models.roomsolution;

public record RoomSolution (int roomSolutionID, int roomTaskID, int roomCustomerID, String path,
    boolean isSuccessfullyTested, boolean isAccepted) {}
