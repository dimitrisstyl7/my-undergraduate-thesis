package com.dimstyl.dietitianhub.email;

import jakarta.mail.MessagingException;

import java.util.Map;

public interface EmailService {

    void sendEmail(String to, Map<String, String> args) throws MessagingException;

}
