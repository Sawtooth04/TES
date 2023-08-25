package org.sawtooth.controllers;

import org.sawtooth.storage.realizations.Storage;
import org.sawtooth.storage.repositories.customer.abstractions.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    private final Storage storage;

    @Autowired
    public TestController(Storage storage) {
        this.storage = storage;
    }

    @GetMapping("/get")
    public String getTest() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        ICustomerRepository repository = storage.GetRepository(ICustomerRepository.class);
        repository.Get(2);
        System.out.println("Hit me!");
        return "Hello, react!";
    }
}
