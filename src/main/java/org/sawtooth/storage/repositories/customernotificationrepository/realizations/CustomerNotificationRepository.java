package org.sawtooth.storage.repositories.customernotificationrepository.realizations;

import org.sawtooth.models.customernotification.CustomerNotification;
import org.sawtooth.models.customernotification.CustomerNotificationMapper;
import org.sawtooth.storage.repositories.customernotificationrepository.abstractions.ICustomerNotificationRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import java.util.List;

public class CustomerNotificationRepository implements ICustomerNotificationRepository {
    private JdbcTemplate template;

    @Override
    public void SetJbdcTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public List<CustomerNotification> Get(int customerID, int start, int count) {
        return template.query("SELECT * FROM get_customer_notifications(?, ?, ?)", new CustomerNotificationMapper(),
            customerID, start, count);
    }

    @Override
    public void AddRoomCustomerNotification(int customerID, String header, String text) {
        template.queryForObject("SELECT * FROM insert_customer_notification(?, ?, ?)", new SingleColumnRowMapper<>(),
            customerID, header, text);
    }

    @Override
    public void AddRoomCustomersNotification(int roomID, String header, String text) {
        template.queryForObject("SELECT * FROM insert_customers_notification(?, ?, ?)", new SingleColumnRowMapper<>(),
            roomID, header, text);
    }
}
