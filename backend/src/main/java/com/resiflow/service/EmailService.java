package com.resiflow.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    public void sendToAdmins(final List<String> recipients, final String subject, final String body) {
        LOGGER.info("Email admins recipients={} subject={} body={}", recipients, subject, body);
    }

    public void sendToUser(final String recipient, final String subject, final String body) {
        LOGGER.info("Email user recipient={} subject={} body={}", recipient, subject, body);
    }
}
