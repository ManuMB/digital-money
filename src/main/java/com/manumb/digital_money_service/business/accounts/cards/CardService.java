package com.manumb.digital_money_service.business.accounts.cards;

import java.util.List;

public interface CardService {
    void createCard(Long accountId, Card card);
    Card findById(Long id);
    List<Card> findAllCards();
}
