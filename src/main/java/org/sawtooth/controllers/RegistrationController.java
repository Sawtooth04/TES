package org.sawtooth.controllers;

import org.sawtooth.models.customer.Customer;
import org.sawtooth.models.customer.CustomerRegistrationModel;
import org.sawtooth.storage.abstractions.IStorage;
import org.sawtooth.storage.repositories.customer.abstractions.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
    private final IStorage storage;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(IStorage storage, PasswordEncoder passwordEncoder) {
        this.storage = storage;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public void Register(@RequestBody CustomerRegistrationModel registrationModel) {
        try {
            Customer customer = new Customer(
                -1,
                registrationModel.name(),
                passwordEncoder.encode(registrationModel.password()),
                registrationModel.email(),
                -1);
            storage.GetRepository(ICustomerRepository.class).Add(customer);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
