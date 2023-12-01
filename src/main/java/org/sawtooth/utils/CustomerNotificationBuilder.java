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

    public CustomerNotification BuildAcceptedSolutionNotification(Room room, RoomTask roomTask) {
        return new CustomerNotification(-1,
            room.name(),
            String.format("Ваше решение для задания %s было принято", roomTask.name())
        );
    }

    public CustomerNotification BuildDeclinedSolutionNotification(Room room, RoomTask roomTask) {
        return new CustomerNotification(-1,
                room.name(),
                String.format("Ваше решение для задания %s было отклонено", roomTask.name())
        );
    }

    public CustomerNotification BuildMessageNotification(String sender, Room room, RoomTask roomTask) {
        return new CustomerNotification(-1,
            String.format("%s: %s", room.name(), roomTask.name()),
            String.format("Вам пришло новое сообщение от %s", sender)
        );
    }
}
