package org.sawtooth.controllers;

import org.sawtooth.models.customernotification.CustomerNotification;
import org.sawtooth.models.customernotification.SetIsReadCustomerNotificationModel;
import org.sawtooth.storage.abstractions.IStorage;
import org.sawtooth.storage.repositories.customer.abstractions.ICustomerRepository;
import org.sawtooth.storage.repositories.customernotificationrepository.abstractions.ICustomerNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer-notification")
public class CustomerNotificationController {
    private final IStorage storage;

    @Autowired
    public CustomerNotificationController(IStorage storage) {
        this.storage = storage;
    }

    @GetMapping("/get-page")
    public List<CustomerNotification> GetPage(int start, int count) throws InstantiationException {
        int customerID = storage.GetRepository(ICustomerRepository.class).Get(SecurityContextHolder.getContext()
            .getAuthentication().getName()).customerID();

        return storage.GetRepository(ICustomerNotificationRepository.class).Get(customerID, start, count);
    }

    @PostMapping("/set-is-read")
    public void SetIsRead(@RequestBody SetIsReadCustomerNotificationModel model) throws InstantiationException {
        storage.GetRepository(ICustomerNotificationRepository.class).SetIsRead(model.customerNotificationID());
    }
}
