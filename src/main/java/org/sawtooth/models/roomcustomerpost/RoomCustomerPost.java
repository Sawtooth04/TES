package org.sawtooth.models.roomcustomerpost;

import java.sql.Timestamp;

public record RoomCustomerPost(int roomCustomerPostID, int roomCustomerID, Timestamp posted, String text) {
    public RoomCustomerPost WithRoomCustomerID(int id) {
        return new RoomCustomerPost(roomCustomerPostID, id, posted, text);
    }
}
