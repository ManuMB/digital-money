package com.manumb.digital_money_service.presentation.controllers;

import com.manumb.digital_money_service.business.accounts.AccountService;
import com.manumb.digital_money_service.business.accounts.cards.dto.RequestRegisterNewCard;
import com.manumb.digital_money_service.business.accounts.cards.dto.ResponseGetCard;
import com.manumb.digital_money_service.business.accounts.dto.RequestUpdateAccountInfo;
import com.manumb.digital_money_service.business.accounts.dto.ResponseGetAccountInfo;
import com.manumb.digital_money_service.business.accounts.dto.ResponseGetBalanceAccount;
import com.manumb.digital_money_service.business.accounts.transactions.dto.RequestCreateNewCardDepositTransaction;
import com.manumb.digital_money_service.business.accounts.transactions.dto.RequestCreateNewTransferTransaction;
import com.manumb.digital_money_service.business.accounts.transactions.dto.ResponseGetTransaction;
import com.manumb.digital_money_service.business.jwt.JwtService;
import com.manumb.digital_money_service.orchestrator.accounts.AccountUseCaseOrchestrator;
import com.manumb.digital_money_service.orchestrator.accounts.cards.CardUseCaseOrchestrator;
import com.manumb.digital_money_service.orchestrator.accounts.transactions.TransactionUseCaseOrchestrator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts/{accountId}")
@Tag(name = "Accounts", description = "API para cuentas bancarias")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountUseCaseOrchestrator accountUseCaseOrchestrator;
    private final TransactionUseCaseOrchestrator transactionUseCaseOrchestrator;
    private final CardUseCaseOrchestrator cardUseCaseOrchestrator;
    private final JwtService jwtService;

    @Operation(summary = "Realizar transferencia",
            description = "Realiza una transferencia de dinero por id de cuenta y cvu/alias de otra cuenta.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "400", description = "Cuenta inexistente", content = @Content),
            @ApiResponse(responseCode = "410", description = "Fondos insuficientes", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    @PostMapping("/transfer")
    public ResponseEntity<String> accountTransfer(@Parameter(description = "ID de la cuenta", required = true) @PathVariable Long accountId,
                                                  @Parameter(description = "Body de la transferencia a crear", required = true) @Valid @RequestBody RequestCreateNewTransferTransaction request) {
        jwtService.verifyAuthorization(accountId);
        transactionUseCaseOrchestrator.createTransferTransaction(accountId, request);
        return ResponseEntity.status(HttpStatus.OK).body("Transfer successful");
    }

    @Operation(summary = "Ingresar dinero",
            description = "Ingresa dinero a la cuenta desde una tarjeta por id de cuenta y detalles de la tarjeta existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    @PostMapping("/deposit")
    public ResponseEntity<String> cardDeposit(@Parameter(description = "ID de la cuenta", required = true) @PathVariable Long accountId,
                                              @Parameter(description = "Body del deposito a crear", required = true) @Valid @RequestBody RequestCreateNewCardDepositTransaction request) {
        jwtService.verifyAuthorization(accountId);
        transactionUseCaseOrchestrator.createCardDepositTransaction(accountId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Deposit successful");
    }

    @Operation(summary = "Obtener informacion",
            description = "Obtiene el cvu y alias de la cuenta por id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    @GetMapping
    public ResponseEntity<ResponseGetAccountInfo> getAccountInfo(@Parameter(description = "ID de la cuenta", required = true) @PathVariable Long accountId) {
        jwtService.verifyAuthorization(accountId);
        ResponseGetAccountInfo response = accountUseCaseOrchestrator.getAccountInfo(accountId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener balance",
            description = "Obtiene el balance de la cuenta por id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    @GetMapping("/balance")
    public ResponseEntity<ResponseGetBalanceAccount> getAccountBalance(@Parameter(description = "ID de la cuenta", required = true) @PathVariable Long accountId) {
        jwtService.verifyAuthorization(accountId);
        ResponseGetBalanceAccount response = accountUseCaseOrchestrator.getBalance(accountId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener ultimas transacciones",
            description = "Obtiene las ultimas 5 transacciones de la cuenta.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    @GetMapping("/transactions")
    public ResponseEntity<List<ResponseGetTransaction>> getLastTransactionsForAccount(@Parameter(description = "ID de la cuenta", required = true) @PathVariable Long accountId) {
        jwtService.verifyAuthorization(accountId);
        List<ResponseGetTransaction> transactions = transactionUseCaseOrchestrator.getLastFiveTransactionsForAccount(accountId);
        return ResponseEntity.ok(transactions);
    }

    @Operation(summary = "Obtener todas las transacciones",
            description = "Obtiene todas las transacciones de la cuenta.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    @GetMapping("/activity")
    public ResponseEntity<List<ResponseGetTransaction>> getAllTransactionsForAccount(@Parameter(description = "ID de la cuenta", required = true) @PathVariable Long accountId) {
        jwtService.verifyAuthorization(accountId);
        List<ResponseGetTransaction> transactions = transactionUseCaseOrchestrator.getAllTransactionsForAccount(accountId);
        return ResponseEntity.ok(transactions);
    }

    @Operation(summary = "Obtener transaccion",
            description = "Obtiene una transaccion por id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "404", description = "Transaccion no encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    @GetMapping("/activity/{transactionId}")
    public ResponseEntity<ResponseGetTransaction> getTransactionById(
            @Parameter(description = "ID de la cuenta", required = true) @PathVariable Long accountId,
            @Parameter(description = "ID de la transaccion", required = true) @PathVariable Long transactionId) {
        jwtService.verifyAuthorization(accountId);
        ResponseGetTransaction transaction = transactionUseCaseOrchestrator.getTransactionById(accountId, transactionId);
        return ResponseEntity.ok(transaction);
    }

    @Operation(summary = "Obtener transacciones filtrando.",
            description = "Obtiene todas las transacciones de la cuenta donde el monto esté dentro de los valores indicados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    @GetMapping("/transactions/filter")
    public ResponseEntity<List<ResponseGetTransaction>> getTransactionsByAmountRange(@Parameter(description = "ID de la cuenta", required = true) @PathVariable Long accountId,
                                                                                     @Parameter(description = "Monto minimo y maximo para filtrar", required = true) @RequestParam Double minAmount, Double maxAmount) {
        jwtService.verifyAuthorization(accountId);
        List<ResponseGetTransaction> transactions = transactionUseCaseOrchestrator
                .getTransactionsByAccountIdAndAmountRange(accountId, minAmount, maxAmount);
        return ResponseEntity.ok(transactions);
    }

    @Operation(summary = "Actualizar informacion",
            description = "Actualiza el alias de la cuenta por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)})
    @PatchMapping
    public ResponseEntity<String> updateAccountInfo(@Parameter(description = "ID de la cuenta", required = true) @PathVariable Long accountId,
                                                    @Parameter(description = "Body que contenga el alias a actualizar", required = true) @Valid @RequestBody RequestUpdateAccountInfo request) {
        jwtService.verifyAuthorization(accountId);
        accountUseCaseOrchestrator.updateAlias(accountId, request);
        return ResponseEntity.status(HttpStatus.OK).body("Account alias updated successfully");
    }

    @Operation(summary = "Registrar tarjeta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "409", description = "Tarjeta asociada a otra cuenta", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)})
    @PostMapping("/cards")
    public ResponseEntity<String> createCard(@Parameter(description = "ID de la cuenta", required = true) @PathVariable Long accountId,
                                             @Parameter(description = "Body de la tarjeta a crear", required = true) @Valid @RequestBody RequestRegisterNewCard requestRegisterNewCard) {
        jwtService.verifyAuthorization(accountId);
        cardUseCaseOrchestrator.createCard(accountId, requestRegisterNewCard);
        return ResponseEntity.status(HttpStatus.CREATED).body("Card created successfully");
    }

    @Operation(summary = "Obtener tarjeta",
            description = "Obtiene una tarjeta asociada a una cuenta por id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "500", description = "Cuenta no encontrada", content = @Content)})
    @GetMapping("/cards/{cardId}")
    public ResponseEntity<ResponseGetCard> getCardById(@Parameter(description = "ID de la cuenta", required = true) @PathVariable Long accountId,
                                                       @Parameter(description = "ID del usuario", required = true) @PathVariable Long cardId) {
        jwtService.verifyAuthorization(accountId);
        try {
            ResponseGetCard response = cardUseCaseOrchestrator.getCardById(cardId, accountId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Operation(summary = "Obtener todas las tarjetas",
            description = "Obtiene todas las tarjetas asociadas a una cuenta por id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "500", description = "Cuenta no encontrada", content = @Content)})
    @GetMapping("/cards")
    public ResponseEntity<?> getAllCardsByAccountId(@Parameter(description = "ID de la cuenta", required = true) @PathVariable Long accountId) {
        jwtService.verifyAuthorization(accountId);
        List<ResponseGetCard> cards = cardUseCaseOrchestrator.getAllCardsByAcountId(accountId);
        if (cards.isEmpty()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.ok(cards);
        }
    }

    @Operation(summary = "Eliminar tarjeta",
            description = "Elimina una tarjeta por id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Tarjeta no encontrada", content = @Content)})
    @DeleteMapping("/cards/{cardId}")
    public ResponseEntity<String> deleteCardById(@Parameter(description = "ID de la cuenta", required = true) @PathVariable Long accountId,
                                                 @Parameter(description = "ID del usuario", required = true) @PathVariable Long cardId) {
        jwtService.verifyAuthorization(accountId);
        cardUseCaseOrchestrator.deleteCardById(cardId, accountId);
        return ResponseEntity.ok("Card deleted successfully");
    }
}
