package org.sawtooth.controllers;

import org.sawtooth.models.customer.Customer;
import org.sawtooth.models.customer.CustomerRegistrationModel;
import org.sawtooth.models.customervalidator.registrationvalidator.RegistrationValidationResults;
import org.sawtooth.models.customervalidator.registrationvalidator.RegistrationValidator;
import org.sawtooth.services.emailmessagesbuilder.IEmailMessagesBuilder;
import org.sawtooth.services.emailservice.IEmailService;
import org.sawtooth.services.jwtbuilder.IJWTBuilder;
import org.sawtooth.storage.abstractions.IStorage;
import org.sawtooth.storage.repositories.customer.abstractions.ICustomerRepository;
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
    private final IEmailMessagesBuilder emailMessagesBuilder;
    private final IJWTBuilder jwtBuilder;

    @Autowired
    public RegistrationController(IStorage storage, PasswordEncoder passwordEncoder, IEmailService emailService,
        IEmailMessagesBuilder emailMessagesBuilder, IJWTBuilder jwtBuilder) {
        this.storage = storage;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.emailMessagesBuilder = emailMessagesBuilder;
        this.jwtBuilder = jwtBuilder;
    }

    @PostMapping("/register")
    @ResponseBody
    public RegistrationValidationResults Register(@RequestBody CustomerRegistrationModel registrationModel) {
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
