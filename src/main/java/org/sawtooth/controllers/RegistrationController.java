package org.sawtooth.controllers;

import org.sawtooth.models.Customer.Customer;
import org.sawtooth.storage.realizations.Storage;
import org.sawtooth.storage.repositories.customer.abstractions.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
    private final Storage storage;

    @Autowired
    public RegistrationController(Storage storage) {
        this.storage = storage;
    }

    @PostMapping("/register")
    public void register(@RequestBody Customer customer) {
        try {
            storage.GetRepository(ICustomerRepository.class).Add(customer);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
