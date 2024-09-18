package com.manumb.digital_money_service.presentation.controllers;

import com.manumb.digital_money_service.business.users.UserService;
import com.manumb.digital_money_service.business.users.dto.*;
import com.manumb.digital_money_service.orchestrator.users.UserUseCaseOrchestrator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "API para cuentas de usuario")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserUseCaseOrchestrator userUseCaseOrchestrator;

    @Operation(summary = "Registrar nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    @PostMapping("/register")
    ResponseEntity<ResponseRegisterNewUser> registerNewUser(@RequestBody @Valid RequestRegisterNewUser ownerUserData) throws MessagingException, IOException {
        ResponseRegisterNewUser response = userUseCaseOrchestrator.register(ownerUserData);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Actualizar usuario",
            description = "Actualiza la informacion de un usuario existente por id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    @PatchMapping("/{id}")
    ResponseEntity<ResponseUpdateUser> updateUser(@PathVariable Long id,
                                                  @Valid @RequestBody RequestUpdateUser requestUpdateUser) throws IOException {
        ResponseUpdateUser response = userUseCaseOrchestrator.update(id, requestUpdateUser);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Eliminar usuario",
            description = "Elimina a un usuario existente por id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userUseCaseOrchestrator.deleteUser(id);
        return ResponseEntity.ok("User successfully deleted");
    }

    @Operation(summary = "Obtener informacion de usuario",
            description = "Obtiene toda la informacion del usuario por id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    @GetMapping("{id}")
    ResponseEntity<ResponseGetUser> getUser(@PathVariable Long id) {
        ResponseGetUser response = userUseCaseOrchestrator.getUser(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Solicitar cambio de contraseña",
            description = "Envia token de cambio de contraseña al correo indicado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    @PostMapping("/recover-password")
    public ResponseEntity<String> sendRecoverPasswordEmail(@RequestBody @Valid RequestEmailUser requestEmailUser) throws MessagingException, IOException {
        userUseCaseOrchestrator.sendRecoverPasswordEmail(requestEmailUser.email());
        return ResponseEntity.ok("Email sent");
    }

    @Operation(summary = "Cambiar contraseña",
            description = "Modifica la contraseña del usario. Se requiere el token provisto por el endpoint recover-password.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    @PatchMapping(path = "modify-password")
    public ResponseEntity<String> modifyPassword(@RequestBody @Valid RequestChangePasswordUser requestChangePasswordUser) throws Exception {
        userUseCaseOrchestrator.changePassword(requestChangePasswordUser);
        return ResponseEntity.ok("Password changed successfully");
    }

    @Operation(summary = "Confirmar email y habilitar usuario",
            description = "Confirma el email y habilita el usuario. Se requiere el token recibido por correo al email registrado. No se podrá realizar login hasta no confirmar el email.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    @PatchMapping(path = "confirm-email")
    public ResponseEntity<String> confirmEmail(@RequestBody @Valid RequestConfirmEmailUser requestConfirmEmailUser) {
        userUseCaseOrchestrator.enableUser(requestConfirmEmailUser.token());
        return ResponseEntity.ok("User enabled succesfully");
    }
}
