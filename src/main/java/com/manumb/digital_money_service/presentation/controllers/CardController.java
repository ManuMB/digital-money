package com.manumb.digital_money_service.presentation.controllers;

import com.manumb.digital_money_service.business.accounts.cards.dto.RequestRegisterNewCard;
import com.manumb.digital_money_service.business.accounts.cards.dto.ResponseGetCard;
import com.manumb.digital_money_service.business.exceptions.ConflictException;
import com.manumb.digital_money_service.orchestrator.accounts.cards.CardUseCaseOrchestrator;
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
@RequestMapping("/api/accounts/{accountId}/cards")
@Tag(name = "Cards", description = "API para tarjetas de credito/debito")
@AllArgsConstructor
public class CardController {
    private final CardUseCaseOrchestrator cardUseCaseOrchestrator;

    @Operation(summary = "Registrar tarjeta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "409", description = "Tarjeta asociada a otra cuenta", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content)})
    @PostMapping
    public ResponseEntity<String> createCard(@Parameter(description = "ID de la cuenta", required = true) @PathVariable Long accountId,
                                             @Parameter(description = "Body de la tarjeta a crear", required = true) @Valid @RequestBody RequestRegisterNewCard requestRegisterNewCard) {
        try {
            cardUseCaseOrchestrator.createCard(accountId, requestRegisterNewCard);
            return ResponseEntity.status(HttpStatus.CREATED).body("Card created successfully");
        } catch (ConflictException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating card");
        }
    }

    @Operation(summary = "Obtener tarjeta",
            description = "Obtiene una tarjeta asociada a una cuenta por id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "500", description = "Cuenta no encontrada", content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<ResponseGetCard> getCardById(@Parameter(description = "ID de la cuenta", required = true) @PathVariable Long accountId,
                                                       @Parameter(description = "ID del usuario", required = true) @PathVariable Long id) {
        try {
            ResponseGetCard response = cardUseCaseOrchestrator.getCardById(id, accountId);
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
    @GetMapping
    public ResponseEntity<?> getCardsByAccountId(@Parameter(description = "ID de la cuenta", required = true) @PathVariable Long accountId) {
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
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCardById(@Parameter(description = "ID de la cuenta", required = true) @PathVariable Long accountId,
                                                 @Parameter(description = "ID del usuario", required = true) @PathVariable Long id) {
        cardUseCaseOrchestrator.deleteCardById(id, accountId);
        return ResponseEntity.ok("Card deleted successfully");
    }
}
