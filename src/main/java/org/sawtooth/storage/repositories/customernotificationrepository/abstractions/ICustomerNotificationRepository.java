package org.sawtooth.storage.repositories.customernotificationrepository.abstractions;

import org.sawtooth.models.customernotification.CustomerNotification;
import org.sawtooth.storage.repositories.IRepository;

import java.util.List;

public interface ICustomerNotificationRepository extends IRepository {
    public List<CustomerNotification> Get(int roomCustomerID, int start, int count);

    public void AddRoomCustomerNotification(int roomCustomerID, String header, String text);

    public void AddRoomCustomersNotification(int roomID, String header, String text);
}
