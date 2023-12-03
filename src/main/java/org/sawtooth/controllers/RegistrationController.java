package org.sawtooth.controllers;

import org.sawtooth.models.customer.Customer;
import org.sawtooth.models.customer.CustomerRegistrationModel;
import org.sawtooth.models.customervalidator.registrationvalidator.RegistrationValidationResults;
import org.sawtooth.models.customervalidator.registrationvalidator.RegistrationValidator;
import org.sawtooth.services.emailservice.IEmailService;
import org.sawtooth.storage.abstractions.IStorage;
import org.sawtooth.storage.repositories.customer.abstractions.ICustomerRepository;
import org.sawtooth.utils.EmailMessagesBuilder;
import org.sawtooth.utils.JWTBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
    private final IStorage storage;
    private final PasswordEncoder passwordEncoder;
    private final IEmailService emailService;
    private final EmailMessagesBuilder emailMessagesBuilder;

    @Autowired
    public RegistrationController(IStorage storage, PasswordEncoder passwordEncoder, IEmailService emailService, EmailMessagesBuilder emailMessagesBuilder) {
        this.storage = storage;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.emailMessagesBuilder = emailMessagesBuilder;
    }

    @PostMapping("/register")
    @ResponseBody
    public RegistrationValidationResults Register(@RequestBody CustomerRegistrationModel registrationModel) {
        JWTBuilder jwtBuilder = new JWTBuilder();
        RegistrationValidationResults results = new RegistrationValidationResults();
        RegistrationValidator validator = new RegistrationValidator(storage, results);
        SimpleMailMessage verificationMessage;
        String verificationToken;

        try {
            if (validator.Validate(registrationModel)) {
                Customer customer = new Customer(-1, registrationModel.name(),
                    passwordEncoder.encode(registrationModel.password()), registrationModel.email(), -1, false);
                verificationToken = jwtBuilder.GetVerificationToken(storage.GetRepository(ICustomerRepository.class).Add(customer));
                verificationMessage = emailMessagesBuilder.BuildEmailConfirmationMessage(customer.email(), verificationToken);
                emailService.sendEmail(verificationMessage);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return results;
    }
}
