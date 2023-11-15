package org.sawtooth.storage.repositories.roomcustomermessage.abstractions;

import org.sawtooth.models.roomcustomermessage.RoomCustomerMessage;
import org.sawtooth.models.roomcustomerpost.RoomCustomerPost;
import org.sawtooth.storage.repositories.IRepository;

import java.util.List;

public interface IRoomCustomerMessageRepository extends IRepository {
    public List<RoomCustomerMessage> Get(int roomTaskID, int roomCustomerID, int start, int count);

    public void AddMemberMessage(int roomCustomerID, int roomTaskID, String text);

    public void AddTeacherMessage(int roomCustomerID, int roomTaskID, String text, int recipient);
}
