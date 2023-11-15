package org.sawtooth.models.roomcustomermessage;

import java.sql.Timestamp;

public record RoomCustomerMessage(int roomCustomerMessageID, String name, String text, Timestamp sent, boolean own) { }
