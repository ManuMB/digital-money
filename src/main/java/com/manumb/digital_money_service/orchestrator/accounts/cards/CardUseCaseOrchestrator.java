package com.manumb.digital_money_service.orchestrator.accounts.cards;

import com.manumb.digital_money_service.business.accounts.cards.Card;
import com.manumb.digital_money_service.business.accounts.cards.dto.RequestRegisterNewCard;
import com.manumb.digital_money_service.business.accounts.cards.dto.ResponseGetCard;
import com.manumb.digital_money_service.business.accounts.cards.dto.ResponseRegisterNewCard;

import java.util.List;

public interface CardUseCaseOrchestrator {
    ResponseRegisterNewCard createCard(Long accountId, RequestRegisterNewCard requestRegisterNewCard);
    ResponseGetCard getCardById(Long id);
    List<Card> getAllCards();
}
