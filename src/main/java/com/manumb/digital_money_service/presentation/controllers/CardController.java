package com.manumb.digital_money_service.presentation.controllers;

import com.manumb.digital_money_service.business.accounts.cards.dto.RequestRegisterNewCard;
import com.manumb.digital_money_service.business.exceptions.ConflictException;
import com.manumb.digital_money_service.orchestrator.accounts.cards.CardUseCaseOrchestrator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts/{accountId}/cards")
public class CardController {
        private final CardUseCaseOrchestrator cardUseCaseOrchestrator;

        public CardController(CardUseCaseOrchestrator cardUseCaseOrchestrator) {
            this.cardUseCaseOrchestrator = cardUseCaseOrchestrator;
        }

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
}
