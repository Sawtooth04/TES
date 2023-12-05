package org.sawtooth.services.customernotificationbuilder;

import org.sawtooth.models.customernotification.CustomerNotification;
import org.sawtooth.models.room.Room;
import org.sawtooth.models.roomtask.RoomTask;

public interface ICustomerNotificationBuilder {
    public CustomerNotification BuildAddedTaskNotification(Room room, RoomTask roomTask);

    public CustomerNotification BuildAcceptedSolutionNotification(Room room, RoomTask roomTask);

    public CustomerNotification BuildDeclinedSolutionNotification(Room room, RoomTask roomTask);

    public CustomerNotification BuildMessageNotification(String sender, Room room, RoomTask roomTask);
}
