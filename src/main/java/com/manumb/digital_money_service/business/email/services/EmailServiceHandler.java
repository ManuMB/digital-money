package com.manumb.digital_money_service.business.email.services;

import com.manumb.digital_money_service.business.email.SendEmail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
        /*
@Service
@EnableAsync
public class EmailServiceHandler implements SendEmail {
    private final JavaMailSender emailSender;

    public EmailServiceHandler(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    private final String bccAddress = "sebastian.goni@byroncode.com";  // Dirección BCC fija

    private void sendEmailToUser(String to, String email, String subject) throws MessagingException {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom("no-reply@byroncode.com");
            emailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }


    @Override
    @Async
    public void sendRecoverPassEmail(String fullName, String email, String token) throws IOException, MessagingException {

        String emailLink = "https://backoffice.byroncode.online/confirmRecoveryPassword?token=" + token;
        String subject = "Password change request";

        String emailBuilt = processEmailForPasswordChange(fullName, email, emailLink);
        this.sendEmailToUser(email, emailBuilt, subject);
    }

    @Override
    @Async
    public void sendConfirmationEmail(String fullName, String email, String token) throws IOException, MessagingException {

        String emailLink = "https://backoffice.byroncode.online/confirmRegister?token=" + token;
        String subject = "Confirmation email";

        String emailBuilt = this.processEmailForConfirmation(fullName, emailLink);
        this.sendEmailToUser(email, emailBuilt, subject);

    }



    private String processEmailForConfirmation(String fullName, String emailLink) throws IOException {
        String template = EmailTemplates.getConfirmEmailHtml();
        Map<String, String> variables = new HashMap<>();
        variables.put("name", fullName);
        variables.put("link", emailLink);

        return TemplateProcessor.processTemplate(template, variables);
    }

    private String processEmailForPasswordChange(String fullName, String email, String emailLink) throws IOException {
        String template = EmailTemplates.getRecoverPasswordHtml();
        Map<String, String> variables = new HashMap<>();
        variables.put("name", fullName);
        variables.put("email", email);
        variables.put("link", emailLink);

        return TemplateProcessor.processTemplate(template, variables);
    }



}
     */