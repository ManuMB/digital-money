package com.manumb.digital_money_service.business.accounts.cards;

import java.util.List;

public interface CardService {
    void createCard(Long accountId, Card card);

    Card findByCardId(Long cardId, Long accountId);

    boolean isFoundByCardNumberAndAccountId(String cardNumber, Long accountId);

    List<Card> findAllCardsByAccountId(Long accountId);

    void deleteCard(Long cardId);
}
