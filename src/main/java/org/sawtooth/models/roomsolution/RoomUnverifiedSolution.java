package org.sawtooth.models.roomsolution;

public record RoomUnverifiedSolution(int roomSolutionID, String customerName) {

    public static RoomUnverifiedSolution FromRoomSolutionWithName(RoomSolution roomSolution, String customerName) {
        return new RoomUnverifiedSolution(roomSolution.roomSolutionID(), customerName);
    }
}
