package com.manumb.digital_money_service.orchestrator.accounts.cards;

import com.manumb.digital_money_service.business.accounts.cards.Card;
import com.manumb.digital_money_service.business.accounts.cards.CardService;
import com.manumb.digital_money_service.business.accounts.cards.dto.RequestRegisterNewCard;
import com.manumb.digital_money_service.business.accounts.cards.dto.ResponseGetCard;
import com.manumb.digital_money_service.business.accounts.cards.dto.ResponseRegisterNewCard;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseGetCard getCardById(Long cardId, Long accountId) {
        Card card = cardService.findByCardId(cardId, accountId);
        return new ResponseGetCard(
                card.getCardHolder(),
                card.getCardNumber(),
                card.getCvv(),
                card.getExpirationDate()
        );
    }

    @Override
    public void deleteCardById(Long cardId, Long accountId) {
        Card card = cardService.findByCardId(cardId, accountId);
        cardService.deleteCard(cardId);
    }

    @Override
    public List<ResponseGetCard> getAllCardsByAcountId(Long accountId) {
        List<Card> cards = cardService.findAllCardsByAccountId(accountId);
        return cards.stream()
                .map(card -> new ResponseGetCard(
                        card.getCardHolder(),
                        card.getCardNumber(),
                        card.getCvv(),
                        card.getExpirationDate()
                ))
                .collect(Collectors.toList());
    }


}
