package org.sawtooth.models.customernotification;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerNotificationMapper implements RowMapper<CustomerNotification> {
    @Override
    public CustomerNotification mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return new CustomerNotification(
            resultSet.getInt("customerNotificationID"),
            resultSet.getString("header"),
            resultSet.getString("text")
        );
    }
}
