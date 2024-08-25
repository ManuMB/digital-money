package com.manumb.digital_money_service.orchestrator.accounts.cards;

import com.manumb.digital_money_service.business.accounts.cards.Card;
import com.manumb.digital_money_service.business.accounts.cards.dto.ResponseGetCard;

import java.util.List;

public interface CardUseCaseOrchestrator {
    ResponseGetCard getCardById(Long id);
    List<Card> getAllCards();
}
