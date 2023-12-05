package org.sawtooth.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.sawtooth.models.customer.Customer;
import org.sawtooth.models.jwtverification.JWTVerificationPayload;
import org.sawtooth.services.emailmessagesbuilder.IEmailMessagesBuilder;
import org.sawtooth.services.emailservice.IEmailService;
import org.sawtooth.services.jwtbuilder.IJWTBuilder;
import org.sawtooth.storage.abstractions.IStorage;
import org.sawtooth.storage.repositories.customer.abstractions.ICustomerRepository;
import org.sawtooth.services.emailmessagesbuilder.EmailMessagesBuilder;
import org.sawtooth.services.jwtbuilder.JWTBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/customer/verification")
public class CustomerVerificationController {
    private final IStorage storage;
    private final IEmailService emailService;
    private final IEmailMessagesBuilder emailMessagesBuilder;
    private final IJWTBuilder jwtBuilder;

    @Autowired
    public CustomerVerificationController(IStorage storage, IEmailService emailService,
        IEmailMessagesBuilder emailMessagesBuilder, IJWTBuilder jwtBuilder) {
        this.storage = storage;
        this.emailService = emailService;
        this.emailMessagesBuilder =emailMessagesBuilder;
        this.jwtBuilder = jwtBuilder;
    }

    @GetMapping("/send-new-message")
    public void SendNewMessage(String name) {
        SimpleMailMessage verificationMessage;
        String verificationToken;
        Customer customer;

        try {
            customer = storage.GetRepository(ICustomerRepository.class).Get(name);
            verificationToken = jwtBuilder.GetVerificationToken(customer.customerID());
            verificationMessage = emailMessagesBuilder.BuildEmailConfirmationMessage(customer.email(), verificationToken);
            emailService.sendEmail(verificationMessage);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @GetMapping("/verify")
    public boolean Verify(String token) {
        try {
            ObjectReader reader = new ObjectMapper().readerFor(JWTVerificationPayload.class);
            String[] parts = token.split("\\.");
            JWTVerificationPayload payload = reader.readValue(Base64.getDecoder().decode(parts[1]));

            if (Objects.equals(parts[2], jwtBuilder.GetVerificationSignature(payload.customerID(), payload.exp()))
                && payload.exp() > new Date().getTime()) {
                storage.GetRepository(ICustomerRepository.class).SetVerified(payload.customerID());
                return true;
            }
            return false;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
