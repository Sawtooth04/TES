package org.sawtooth.services.emailmessagesbuilder;

import org.springframework.mail.SimpleMailMessage;

public interface IEmailMessagesBuilder {
    public SimpleMailMessage BuildEmailConfirmationMessage(String recipient, String token);
}
