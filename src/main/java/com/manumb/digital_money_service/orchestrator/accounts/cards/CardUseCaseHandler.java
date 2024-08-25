package com.manumb.digital_money_service.orchestrator.accounts.cards;

import com.manumb.digital_money_service.business.accounts.cards.Card;
import com.manumb.digital_money_service.business.accounts.cards.CardService;
import com.manumb.digital_money_service.business.accounts.cards.dto.ResponseGetCard;

import java.util.List;

public class CardUseCaseHandler implements CardUseCaseOrchestrator{
    private final CardService cardService;

    public CardUseCaseHandler(CardService cardService) {
        this.cardService = cardService;
    }

    @Override
    public ResponseGetCard getCardById(Long id) {
        Card card = cardService.findById(id);
        return new ResponseGetCard(
                card.getCardHolder(),
                card.getCardNumber(),
                card.getCvv(),
                card.getExpirationDate()
        );
    }

    @Override
    public List<Card> getAllCards() {
        return cardService.findAllCards();
    }
}
