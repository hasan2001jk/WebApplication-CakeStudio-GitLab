package ru.samgups.cakestudio.service;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import ru.samgups.cakestudio.dto.EmailRequest;

import javax.mail.MessagingException;

@SpringBootTest
public class EmailSenderServiceTest {

    @Autowired
    private EmailSenderService emailSenderService;


    @Test
    public void sendHtmlMessageTest() throws MessagingException {
        EmailRequest email = new EmailRequest();
        email.setFirstName("Khasan");
        email.setLastName("Abdurakhmanov");
        email.setEmail("ya.hasan2001@yandex.ru");
//        Assertions.assertDoesNotThrow(() -> emailSenderService.sendHtmlMessage(email));
    }
}