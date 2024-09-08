package com.manumb.digital_money_service.orchestrator.users;

import com.manumb.digital_money_service.business.users.dto.*;
import jakarta.mail.MessagingException;

import java.io.IOException;

public interface UserUseCaseOrchestrator {
    ResponseRegisterNewUser register(RequestRegisterNewUser userData) throws MessagingException, IOException;

    ResponseUpdateUser update(Long id, RequestUpdateUser userData) throws IOException;

    void deleteUser(Long id);

    ResponseGetUser getUser(Long id);

    void enableUser(String token);

    void sendRecoverPasswordEmail(String email) throws MessagingException, IOException;

    void changePassword(RequestChangePasswordUser requestChangePasswordUser);

    void resendConfirmationEmail(String email) throws MessagingException, IOException;
}
