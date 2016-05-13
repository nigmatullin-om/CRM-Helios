package com.becomejavasenior.service.impl;

import com.becomejavasenior.model.User;
import com.becomejavasenior.service.MailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MailServiceImpl implements MailService {
    private static final Logger LOGGER = LogManager.getLogger(MailServiceImpl.class);
    private final String MAIL_PROPERTIES = "mail.properties";
    private final String SMPT = "smtp";
    private final String LOGIN = "login";
    private final String PASS = "pass";

    public void sendRegistrationMail(String topic, String body, User user) throws MessagingException {
        Properties properties = getProperties();
        Session mailSession = Session.getDefaultInstance(properties, null);
        MimeMessage mimeMessage = new MimeMessage(mailSession);
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
        mimeMessage.setSubject(topic);
        mimeMessage.setContent(String.format(body, user.getName(), user.getEmail(), user.getPassword()), "text/html");
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
}


