package com.manumb.digital_money_service.orchestrator.accounts.cards;

import com.manumb.digital_money_service.business.accounts.cards.dto.RequestRegisterNewCard;
import com.manumb.digital_money_service.business.accounts.cards.dto.ResponseGetCard;

import java.util.List;

public interface CardUseCaseOrchestrator {
    void createCard(Long accountId, RequestRegisterNewCard requestRegisterNewCard);

    ResponseGetCard getCardById(Long cardId, Long accountId);

    void deleteCardById(Long cardId, Long accountId);

    List<ResponseGetCard> getAllCardsByAcountId(Long accountId);
}
