package com.manumb.digital_money_service.orchestrator.accounts.cards;

import com.manumb.digital_money_service.business.accounts.cards.Card;
import com.manumb.digital_money_service.business.accounts.cards.CardService;
import com.manumb.digital_money_service.business.accounts.cards.dto.RequestRegisterNewCard;
import com.manumb.digital_money_service.business.accounts.cards.dto.ResponseGetCard;
import com.manumb.digital_money_service.business.accounts.cards.dto.ResponseRegisterNewCard;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CardUseCaseHandler implements CardUseCaseOrchestrator{
    private final CardService cardService;

    public CardUseCaseHandler(CardService cardService) {
        this.cardService = cardService;
    }

    @Override
    public ResponseRegisterNewCard createCard(Long accountId, RequestRegisterNewCard requestRegisterNewCard) {
        Card card = new Card();
        card.setCardHolder(requestRegisterNewCard.cardHolder());
        card.setCardNumber(requestRegisterNewCard.cardNumber());
        card.setCvv(requestRegisterNewCard.cvv());
        card.setExpirationDate(requestRegisterNewCard.expirationDate());
        cardService.createCard(accountId, card);

        return new ResponseRegisterNewCard(
                card.getCardHolder(),
                card.getCardNumber(),
                card.getCvv(),
                card.getExpirationDate()
        );
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
