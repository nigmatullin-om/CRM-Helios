package com.becomejavasenior.service;

import com.becomejavasenior.model.User;
import com.becomejavasenior.service.impl.MailServiceImpl;
import org.junit.Test;

import javax.mail.MessagingException;

import static org.junit.Assert.*;

public class MailServiceImplTest {

    @Test
    public void testSendRegistrationMail() throws Exception {
        MailService mailService = new MailServiceImpl();
        User user = new User();
        user.setName("Yaroslav");
        user.setEmail("yaroslaff.k@gmail.com");
        try {
            mailService.sendRegistrationMail("You have been registered on CRM-HELIOS",
                    "Hello, %s! <br> <br> recently you've been registered on CRM-HELIOS <br> your email: %s <br> your password: %s <br> <br> Sincerely yours, CRM-HELIOS team!", user);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}