package org.sawtooth.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.sawtooth.models.customer.Customer;
import org.sawtooth.models.jwtroomlink.JWTRoomLinkPayload;
import org.sawtooth.models.jwtverification.JWTVerificationPayload;
import org.sawtooth.storage.abstractions.IStorage;
import org.sawtooth.storage.repositories.customer.abstractions.ICustomerRepository;
import org.sawtooth.storage.repositories.roomcustomer.abstractions.IRoomCustomerRepository;
import org.sawtooth.utils.JWTBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/customer/verification")
public class CustomerVerificationController {
    private final IStorage storage;

    @Autowired
    public CustomerVerificationController(IStorage storage) {
        this.storage = storage;
    }

    @GetMapping("/verify")
    public boolean Verify(String token) {
        try {
            JWTBuilder builder = new JWTBuilder();
            ObjectReader reader = new ObjectMapper().readerFor(JWTVerificationPayload.class);
            String[] parts = token.split("\\.");
            JWTVerificationPayload payload = reader.readValue(Base64.getDecoder().decode(parts[1]));

            if (Objects.equals(parts[2], builder.GetVerificationSignature(payload.customerID(), payload.exp()))
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
