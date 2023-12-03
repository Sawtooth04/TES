package org.sawtooth.services.emailservice;

import org.springframework.mail.SimpleMailMessage;

public interface IEmailService {
    public void sendEmail(SimpleMailMessage message);
}
