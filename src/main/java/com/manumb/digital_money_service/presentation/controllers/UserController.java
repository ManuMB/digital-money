package com.manumb.digital_money_service.presentation.controllers;

import com.manumb.digital_money_service.business.users.UserService;
import com.manumb.digital_money_service.business.users.dto.*;
import com.manumb.digital_money_service.orchestrator.users.UserUseCaseOrchestrator;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserUseCaseOrchestrator userUseCaseOrchestrator;

    public UserController(UserService userService, UserUseCaseOrchestrator userUseCaseOrchestrator) {
        this.userService = userService;
        this.userUseCaseOrchestrator = userUseCaseOrchestrator;
    }

    @PostMapping("/register")
    ResponseEntity<ResponseRegisterNewUser> registerNewUser(@RequestBody RequestRegisterNewUser ownerUserData) throws MessagingException, IOException {
        ResponseRegisterNewUser response = userUseCaseOrchestrator.register(ownerUserData);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    ResponseEntity<ResponseUpdateUser> updateUser(@PathVariable Long id, @RequestBody RequestUpdateUser requestUpdateUser) throws IOException {
        ResponseUpdateUser response = userUseCaseOrchestrator.update(id, requestUpdateUser);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteUser(@PathVariable Long id){
        userUseCaseOrchestrator.deleteUser(id);
        return ResponseEntity.ok("User successfully deleted");
    }

    @GetMapping("{id}")
    ResponseEntity<ResponseGetUser> getUser(@PathVariable Long id){
        ResponseGetUser response = userUseCaseOrchestrator.getUser(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/recover-password")
    public ResponseEntity<String> sendRecoverPasswordEmail(@RequestBody RequestEmailUser requestEmailUser) throws MessagingException, IOException {
        userUseCaseOrchestrator.sendRecoverPasswordEmail(requestEmailUser.email());
        return ResponseEntity.ok("Email sent");
    }

    @PatchMapping(path = "modify-password")
    public ResponseEntity<String> modifyPassword(@Valid @RequestBody RequestChangePasswordUser requestChangePasswordUser) throws Exception {
        userUseCaseOrchestrator.changePassword(requestChangePasswordUser);
        return ResponseEntity.ok("Password changed successfully");
    }

    @PatchMapping(path = "confirm-email")
    public ResponseEntity<String> confirmEmail(@RequestBody RequestConfirmEmailUser requestConfirmEmailUser) {
        userUseCaseOrchestrator.enableUser(requestConfirmEmailUser.token());
        return ResponseEntity.ok("User enabled succesfully");
    }

    @PostMapping("resend-email")
    public ResponseEntity<String> resendConfirmationEmail(@RequestBody RequestEmailUser emailUser) throws MessagingException, IOException {
        userUseCaseOrchestrator.resendConfirmationEmail(emailUser.email());
        return ResponseEntity.ok("email forwarded successfully");
    }
}
