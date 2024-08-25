package com.manumb.digital_money_service.presentation.controllers;

import com.manumb.digital_money_service.business.accounts.AccountService;
import com.manumb.digital_money_service.business.accounts.dto.ResponseGetBalanceAccount;
import com.manumb.digital_money_service.orchestrator.accounts.AccountUseCaseOrchestrator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;
    private final AccountUseCaseOrchestrator accountUseCaseOrchestrator;

    public AccountController(AccountService accountService, AccountUseCaseOrchestrator accountUseCaseOrchestrator) {
        this.accountService = accountService;
        this.accountUseCaseOrchestrator = accountUseCaseOrchestrator;
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResponseGetBalanceAccount> getAccountBalance(@PathVariable Long id){
        ResponseGetBalanceAccount response = accountUseCaseOrchestrator.getBalance(id);
        return ResponseEntity.ok(response);
    }
}
