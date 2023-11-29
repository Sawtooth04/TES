package org.sawtooth.utils;

import org.sawtooth.models.customernotification.CustomerNotification;
import org.sawtooth.models.room.Room;
import org.sawtooth.models.roomtask.RoomTask;

public class CustomerNotificationBuilder {
    public CustomerNotification BuildAddedTaskNotification(Room room, RoomTask roomTask) {
        return new CustomerNotification(-1,
                room.name(),
                String.format("Добавлено новое задание: %s", roomTask.name())
            );
    }
}
