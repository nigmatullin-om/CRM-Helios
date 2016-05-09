package com.becomejavasenior.controller;

import com.becomejavasenior.model.User;
import com.becomejavasenior.service.UserService;
import com.becomejavasenior.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MailController {
    private static final Logger LOGGER = LogManager.getLogger(AddDealController.class);
    private final String REGISTRATION_SUBJECT = "registrationSubject";
    private final String MAIL_PROPERTIES = "mail.properties";
    private final String SMPT = "smtp";
    private final String LOGIN = "login";
    private final String PASS = "pass";

    public void sendRegistrationMail(User user) throws MessagingException {
        Properties properties = getProperties();
        Session mailSession = Session.getDefaultInstance(properties, null);
        MimeMessage mimeMessage = new MimeMessage(mailSession);
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
        mimeMessage.setSubject(properties.getProperty(REGISTRATION_SUBJECT));
        mimeMessage.setContent(getRegistrationMessage(user), "text/html");
        Transport transport = mailSession.getTransport(SMPT);
        transport.connect(properties.getProperty(SMPT),properties.getProperty(LOGIN) , properties.getProperty(PASS));
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();
    }

    private Properties getProperties(){
        Properties props = new Properties();
        try(InputStream fis = getClass().getClassLoader().getResourceAsStream(MAIL_PROPERTIES)) {
            props.load(fis);
        } catch (IOException e) {
            LOGGER.error("Getting a properties was failed. Error - {}", new Object[]{e.getMessage()});
        }
        return props;
    }

    private String getRegistrationMessage(User user){
        String message = "Hello, " + user.getName() + "!" + "<br>" + "<br>" +
                        "recently you've been registered on CRM-HELIOS" + "<br>" +
                        "your email: " + user.getEmail() + "<br>" +
                        "your password: " + user.getPassword() + "<br>" + "<br>"+
                        "Sincerely yours, CRM-HELIOS team";
        return message;
    }
}


