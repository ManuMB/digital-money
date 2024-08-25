package com.manumb.digital_money_service.presentation.controllers;

import com.manumb.digital_money_service.business.jwt.JwtService;
import com.manumb.digital_money_service.business.security.exception.IncorrectPasswordException;
import com.manumb.digital_money_service.business.security.exception.UserNotFoundException;
import com.manumb.digital_money_service.orchestrator.auth.AuthUseCaseOrchestrator;
import com.manumb.digital_money_service.orchestrator.auth.dto.RequestUserLogin;
import com.manumb.digital_money_service.orchestrator.auth.dto.ResponseUserLogin;
import com.manumb.digital_money_service.orchestrator.auth.dto.ResponseUserLogout;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "API para autenticación de usuarios")
public class AuthController {

    private final AuthUseCaseOrchestrator authUseCaseOrchestrator;
    private final JwtService jwtService;

    public AuthController(AuthUseCaseOrchestrator authUseCaseOrchestrator, JwtService jwtService) {
        this.authUseCaseOrchestrator = authUseCaseOrchestrator;
        this.jwtService = jwtService;
    }

    @Operation(
            summary = "Ingresar al sistema con credenciales de usuario",
            description = "Permite a un usuario ingresar al sistema utilizando sus credenciales."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseUserLogin.class, name = "ResponseUserLogin"))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<ResponseUserLogin> login (@RequestBody RequestUserLogin requestUserLogin){
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
