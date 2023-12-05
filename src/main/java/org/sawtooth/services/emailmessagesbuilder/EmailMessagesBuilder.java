package org.sawtooth.services.emailmessagesbuilder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailMessagesBuilder implements IEmailMessagesBuilder {
    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public SimpleMailMessage BuildEmailConfirmationMessage(String recipient, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(recipient);
        message.setSubject("Подтверждение регистрации в системе TES");
        message.setText(String.format("%s%s\n\n%s",
            "Для подтверждения регистрации в системе TES, перейдите по следующей ссылке: ",
            String.format("%s%s", "http://localhost:3000/verification/", token),
            "Если письмо было отправлено вам по ошибке, то просто проигнорируйте его."
        ));
        return message;
    }
}
