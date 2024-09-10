package com.manumb.digital_money_service.presentation.controllers;

import com.manumb.digital_money_service.business.jwt.JwtService;
import com.manumb.digital_money_service.orchestrator.auth.AuthUseCaseOrchestrator;
import com.manumb.digital_money_service.orchestrator.auth.dto.RequestUserLogin;
import com.manumb.digital_money_service.orchestrator.auth.dto.ResponseUserLogin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "API para autenticación de usuarios")
@AllArgsConstructor
public class AuthController {

    private final AuthUseCaseOrchestrator authUseCaseOrchestrator;
    private final JwtService jwtService;


    @Operation(
            summary = "Ingresar al sistema con credenciales de usuario",
            description = "Permite a un usuario ingresar al sistema utilizando sus credenciales."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseUserLogin.class, name = "ResponseUserLogin"))),
            @ApiResponse(responseCode = "400", description = "Contraseña incorrecta", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuario no existente", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<ResponseUserLogin> login(@RequestBody RequestUserLogin requestUserLogin) {
        var response = authUseCaseOrchestrator.userLogin(requestUserLogin);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Desloguearse del sistema",
            description = "Permite a un usuario cerrar sesión y revocar sus credenciales."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseUserLogin.class, name = "ResponseUserLogin"))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        return authUseCaseOrchestrator.userLogout(request);
    }

}
