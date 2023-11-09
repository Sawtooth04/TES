package org.sawtooth.models.roomsolution;

public record RoomSolutionResponse(int roomSolutionID, int roomTaskID, int roomCustomerID, boolean isSuccessfullyTested,
    boolean isAccepted) {

    public static RoomSolutionResponse FromRoomSolution(RoomSolution roomSolution) {
        return new RoomSolutionResponse(roomSolution.roomSolutionID(), roomSolution.roomTaskID(), roomSolution.roomCustomerID(),
            roomSolution.isSuccessfullyTested(), roomSolution.isAccepted());
    }
}
