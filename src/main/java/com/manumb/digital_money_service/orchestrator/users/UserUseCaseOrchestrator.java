package com.manumb.digital_money_service.orchestrator.users;

import com.manumb.digital_money_service.business.users.dto.RequestChangePasswordUser;
import com.manumb.digital_money_service.business.users.dto.RequestRegisterNewUser;
import jakarta.mail.MessagingException;

import java.io.IOException;

public interface UserUseCaseOrchestrator {
    void register(RequestRegisterNewUser userData) throws MessagingException, IOException;
    void deleteUser(Long id);
    void enableUser(String token);
    void sendRecoverPasswordEmail(String email) throws MessagingException, IOException;
    void changePassword(RequestChangePasswordUser requestChangePasswordUser);
    void resendConfirmationEmail(String email) throws MessagingException, IOException;
}
