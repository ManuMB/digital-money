package com.manumb.digital_money_service.business.email;

import jakarta.mail.MessagingException;

import java.io.IOException;

public interface SendEmail {
    void sendRecoverPassEmail(String fullName, String email, String token) throws IOException, MessagingException;
    void sendConfirmationEmail(String fullName, String email, String token) throws IOException, MessagingException;
}
