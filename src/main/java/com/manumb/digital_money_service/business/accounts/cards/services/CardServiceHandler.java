package com.manumb.digital_money_service.business.accounts.cards.services;

import com.manumb.digital_money_service.business.accounts.cards.Card;
import com.manumb.digital_money_service.business.accounts.cards.CardService;
import com.manumb.digital_money_service.business.exceptions.NotFoundException;
import com.manumb.digital_money_service.persistence.CardSqlRepository;

import java.util.List;

public class CardServiceHandler implements CardService {
    private final CardSqlRepository cardSqlRepository;

    public CardServiceHandler(CardSqlRepository cardSqlRepository) {
        this.cardSqlRepository = cardSqlRepository;
    }

    @Override
    public void createCard(Long accountId, Card card) {
        //TODO
    }

    @Override
    public Card findById(Long id) {
        return cardSqlRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Card with id " + id + " not found"));
    }

    @Override
    public List<Card> findAllCards() {
        return cardSqlRepository.findAll();
    }
}
