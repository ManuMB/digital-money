package com.manumb.digital_money_service.presentation.controllers;

import com.manumb.digital_money_service.business.accounts.AccountService;
import com.manumb.digital_money_service.business.accounts.dto.ResponseGetBalanceAccount;
import com.manumb.digital_money_service.business.accounts.transactions.dto.ResponseGetTransaction;
import com.manumb.digital_money_service.orchestrator.accounts.AccountUseCaseOrchestrator;
import com.manumb.digital_money_service.orchestrator.accounts.transactions.TransactionUseCaseOrchestrator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;
    private final AccountUseCaseOrchestrator accountUseCaseOrchestrator;
    private final TransactionUseCaseOrchestrator transactionUseCaseOrchestrator;

    public AccountController(AccountService accountService, AccountUseCaseOrchestrator accountUseCaseOrchestrator, TransactionUseCaseOrchestrator transactionUseCaseOrchestrator) {
        this.accountService = accountService;
        this.accountUseCaseOrchestrator = accountUseCaseOrchestrator;
        this.transactionUseCaseOrchestrator = transactionUseCaseOrchestrator;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseGetBalanceAccount> getAccountBalance(@PathVariable Long id){
        ResponseGetBalanceAccount response = accountUseCaseOrchestrator.getBalance(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<List<ResponseGetTransaction>> getLastTransactionsForAccount(@PathVariable Long accountId) {
        List<ResponseGetTransaction> transactions = transactionUseCaseOrchestrator.getLastTransactionsForAccount(accountId);
        return ResponseEntity.ok(transactions);
    }
}
