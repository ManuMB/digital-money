package com.manumb.digital_money_service.presentation.controllers;

import com.manumb.digital_money_service.business.accounts.cards.dto.RequestRegisterNewCard;
import com.manumb.digital_money_service.business.accounts.cards.dto.ResponseGetCard;
import com.manumb.digital_money_service.business.exceptions.ConflictException;
import com.manumb.digital_money_service.orchestrator.accounts.cards.CardUseCaseOrchestrator;
import io.swagger.v3.oas.annotations.tags.Tag;
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


    @PostMapping
    public ResponseEntity<String> createCard(@PathVariable Long accountId, @RequestBody RequestRegisterNewCard requestRegisterNewCard) {
        try {
            cardUseCaseOrchestrator.createCard(accountId, requestRegisterNewCard);
            return ResponseEntity.status(HttpStatus.CREATED).body("Card created successfully");
        } catch (ConflictException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating card");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseGetCard> getCardById(@PathVariable Long accountId, @PathVariable Long id) {
        try {
            ResponseGetCard response = cardUseCaseOrchestrator.getCardById(id, accountId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<?> getCardsByAccountId(@PathVariable Long accountId) {
        List<ResponseGetCard> cards = cardUseCaseOrchestrator.getAllCardsByAcountId(accountId);
        if (cards.isEmpty()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.ok(cards);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCardById(@PathVariable Long accountId, @PathVariable Long id) {
        cardUseCaseOrchestrator.deleteCardById(id, accountId);
        return ResponseEntity.ok("Card deleted successfully");
    }
}
