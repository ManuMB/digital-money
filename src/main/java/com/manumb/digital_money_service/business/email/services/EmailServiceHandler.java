package com.manumb.digital_money_service.business.email.services;


import com.manumb.digital_money_service.business.email.SendEmail;
import com.manumb.digital_money_service.business.email.templateProcessor.EmailTemplates;
import com.manumb.digital_money_service.business.email.templateProcessor.TemplateProcessor;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@EnableAsync
@AllArgsConstructor
public class EmailServiceHandler implements SendEmail {

    private final JavaMailSender emailSender;

    private void sendEmailToUser(String to, String email, String subject) throws MessagingException {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom("//TODO");
            emailSender.send(message);
        } catch (MessagingException e) {
            throw new MessagingException("Failed to send email", e);
        }
    }


    @Override
    @Async
    public void sendRecoverPassEmail(String fullName, String email, String token) throws MessagingException {

        String text = "The password recovery token is: " + token;
        String subject = "Password change request";

        String emailBuilt = processEmailForPasswordChange(fullName, email, text);
        this.sendEmailToUser(email, emailBuilt, subject);
    }

    @Override
    @Async
    public void sendConfirmationEmail(String fullName, String email, String token) throws MessagingException {

        String text = "The confimation token is: " + token;
        String subject = "Confirmation email";

        String emailBuilt = this.processEmailForConfirmation(fullName, text);
        this.sendEmailToUser(email, emailBuilt, subject);

    }



    private String processEmailForConfirmation(String fullName, String text){
        String template = EmailTemplates.getConfirmEmailHtml();
        Map<String, String> variables = new HashMap<>();
        variables.put("name", fullName);
        variables.put("text", text);

        return TemplateProcessor.processTemplate(template, variables);
    }

    private String processEmailForPasswordChange(String fullName, String email, String text){
        String template = EmailTemplates.getRecoverPasswordHtml();
        Map<String, String> variables = new HashMap<>();
        variables.put("name", fullName);
        variables.put("email", email);
        variables.put("text", text);

        return TemplateProcessor.processTemplate(template, variables);
    }



}