package com.becomejavasenior.service;

import com.becomejavasenior.model.User;

import javax.mail.MessagingException;

public interface MailService {
    void sendRegistrationMail(String topic, String body,User user) throws MessagingException;
}
