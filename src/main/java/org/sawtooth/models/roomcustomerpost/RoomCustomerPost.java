package org.sawtooth.models.roomcustomerpost;

import java.sql.Timestamp;

public record RoomCustomerPost(int roomCustomerPostID, int roomCustomerID, Timestamp posted, String text, int customerID,
    String name) {
}
