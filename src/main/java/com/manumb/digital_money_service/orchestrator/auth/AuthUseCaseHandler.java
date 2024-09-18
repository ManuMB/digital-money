package com.manumb.digital_money_service.orchestrator.auth;

import com.manumb.digital_money_service.business.exceptions.BadRequestException;
import com.manumb.digital_money_service.business.jwt.JwtService;
import com.manumb.digital_money_service.business.security.exception.IncorrectPasswordException;
import com.manumb.digital_money_service.business.users.UserService;
import com.manumb.digital_money_service.orchestrator.auth.dto.RequestUserLogin;
import com.manumb.digital_money_service.orchestrator.auth.dto.ResponseUserLogin;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthUseCaseHandler implements AuthUseCaseOrchestrator {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;

    public AuthUseCaseHandler(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserService userService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    public ResponseUserLogin userLogin(RequestUserLogin requestUserLogin) {
        var email = requestUserLogin.email();

        var user = userService.findByEmail(email);
        if (!user.isEnabled()) {
            throw new BadRequestException("User is not enabled");
        }

        try {
            //create an Authentication object with username and password credential
            var abstractionOfAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    email, requestUserLogin.password()
            );

            //invoke 'authenticate' method from authenticationManager for delegate the authentication to pertinent authenticationProvider
            authenticationManager.authenticate(abstractionOfAuthenticationToken);

        } catch (AuthenticationException ex) {
            // If user exists but authentication failed, assume bad credentials
            throw new IncorrectPasswordException("Incorrect password");
        }

        //bring the user from DB
        //var user = userService.findByEmail(email);


        var extraClaims = jwtService.generateExtraClaimsOfUser(user);

        //generate token that after will be placed into response
        var jwt = jwtService.generateToken(extraClaims, user);

        //create a response object
        return new ResponseUserLogin(jwt.getJwt());
    }

    @Override
    public ResponseEntity<String> userLogout(HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("DM-")) {
            return ResponseEntity.internalServerError().body("Invalid token or no token provided");
        }

        // Remove "Bearer " prefix from the token
        String token = authorizationHeader.substring(3);

        jwtService.invalidateToken(token);
        return ResponseEntity.ok("User logged out successfully");
    }


}
