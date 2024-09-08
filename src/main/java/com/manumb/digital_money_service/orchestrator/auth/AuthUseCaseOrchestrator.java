package com.manumb.digital_money_service.orchestrator.auth;

import com.manumb.digital_money_service.orchestrator.auth.dto.RequestUserLogin;
import com.manumb.digital_money_service.orchestrator.auth.dto.ResponseUserLogin;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface AuthUseCaseOrchestrator {
    ResponseUserLogin userLogin(RequestUserLogin requestUserLogin);

    ResponseEntity<String> userLogout(HttpServletRequest request);
}
