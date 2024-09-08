package com.manumb.digital_money_service.presentation.controllers;

import com.manumb.digital_money_service.business.accounts.AccountService;
import com.manumb.digital_money_service.business.accounts.dto.RequestUpdateAccountInfo;
import com.manumb.digital_money_service.business.accounts.dto.ResponseGetAccountInfo;
import com.manumb.digital_money_service.business.accounts.dto.ResponseGetBalanceAccount;
import com.manumb.digital_money_service.business.accounts.transactions.dto.RequestCreateNewCardDepositTransaction;
import com.manumb.digital_money_service.business.accounts.transactions.dto.RequestCreateNewTransferTransaction;
import com.manumb.digital_money_service.business.accounts.transactions.dto.ResponseGetTransaction;
import com.manumb.digital_money_service.business.exceptions.NotFoundException;
import com.manumb.digital_money_service.business.jwt.JwtService;
import com.manumb.digital_money_service.orchestrator.accounts.AccountUseCaseOrchestrator;
import com.manumb.digital_money_service.orchestrator.accounts.transactions.TransactionUseCaseOrchestrator;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@Tag(name = "Accounts", description = "API para cuentas bancarias")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountUseCaseOrchestrator accountUseCaseOrchestrator;
    private final TransactionUseCaseOrchestrator transactionUseCaseOrchestrator;
    private final JwtService jwtService;


    @PostMapping("/{accountId}/transfer")
    public ResponseEntity<String> accountTransfer(@PathVariable Long accountId, @RequestBody RequestCreateNewTransferTransaction request) {
        transactionUseCaseOrchestrator.createTransferTransaction(accountId, request);
        return ResponseEntity.status(HttpStatus.OK).body("Transfer successful");
    }

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<String> cardDeposit(@PathVariable Long accountId, @RequestBody RequestCreateNewCardDepositTransaction request) {
        transactionUseCaseOrchestrator.createCardDepositTransaction(accountId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Deposit successful");
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<ResponseGetAccountInfo> getAccountInfo(@PathVariable Long accountId) {
        ResponseGetAccountInfo response = accountUseCaseOrchestrator.getAccountInfo(accountId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{accountId}/balance")
    public ResponseEntity<ResponseGetBalanceAccount> getAccountBalance(@PathVariable Long accountId) {
        ResponseGetBalanceAccount response = accountUseCaseOrchestrator.getBalance(accountId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<List<ResponseGetTransaction>> getLastTransactionsForAccount(@PathVariable Long accountId) {
        List<ResponseGetTransaction> transactions = transactionUseCaseOrchestrator.getLastFiveTransactionsForAccount(accountId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{accountId}/activity")
    public ResponseEntity<List<ResponseGetTransaction>> getAllTransactionsForAccount(@PathVariable Long accountId) {
        List<ResponseGetTransaction> transactions = transactionUseCaseOrchestrator.getAllTransactionsForAccount(accountId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/{accountId}/activity/{transactionId}")
    public ResponseEntity<ResponseGetTransaction> getTransactionById(@PathVariable Long accountId, @PathVariable Long transactionId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ResponseEntity<?> authorizationResponse = jwtService.verifyAuthorization(authentication, accountId);
        if (authorizationResponse != null) { // Check if authorization failed
            return (ResponseEntity<ResponseGetTransaction>) authorizationResponse; // Cast to the correct type
        }
        try {
            ResponseGetTransaction transaction = transactionUseCaseOrchestrator.getTransactionById(transactionId);
            return ResponseEntity.ok(transaction);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{accountId}")
    public ResponseEntity<String> updateAccountInfo(@PathVariable Long accountId, @RequestBody RequestUpdateAccountInfo request) {
        accountUseCaseOrchestrator.updateAlias(accountId, request);
        return ResponseEntity.status(HttpStatus.OK).body("Account alias updated successfully");
    }
}
