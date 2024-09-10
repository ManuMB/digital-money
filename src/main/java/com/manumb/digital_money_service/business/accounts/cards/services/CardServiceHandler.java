package com.manumb.digital_money_service.business.accounts.cards.services;

import com.manumb.digital_money_service.business.accounts.Account;
import com.manumb.digital_money_service.business.accounts.AccountService;
import com.manumb.digital_money_service.business.accounts.cards.Card;
import com.manumb.digital_money_service.business.accounts.cards.CardService;
import com.manumb.digital_money_service.business.accounts.cards.exception.CardNotFoundException;
import com.manumb.digital_money_service.business.exceptions.ConflictException;
import com.manumb.digital_money_service.business.exceptions.NotFoundException;
import com.manumb.digital_money_service.persistence.AccountSqlRepository;
import com.manumb.digital_money_service.persistence.CardSqlRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CardServiceHandler implements CardService {

    private final CardSqlRepository cardSqlRepository;
    private final AccountService accountService;

    @Override
    public void createCard(Long accountId, Card card) {
        if (cardSqlRepository.existsByCardNumberAndAccountIdNot(card.getCardNumber(), accountId)) {
            throw new ConflictException("Card number is already associated with another account");
        }
        if (cardSqlRepository.existsByCardHolderAndCardNumberAndCvvAndExpirationDateAndAccountId(
                card.getCardHolder(), card.getCardNumber(), card.getCvv(), card.getExpirationDate(), accountId)) {
            throw new ConflictException("A card with the same details already exists for this account");
        }

        Account account = accountService.findById(accountId);
        card.setAccount(account);

        cardSqlRepository.save(card);
    }

    @Override
    public Card findByCardId(Long cardId, Long accountId) {
        return cardSqlRepository.findByIdAndAccountId(cardId, accountId)
                .orElseThrow(() -> new CardNotFoundException("Card with id " + cardId + " not found for account " + accountId));
    }

    @Override
    public boolean isFoundByCardNumberAndAccountId(String cardNumber, Long accountId) {
        return cardSqlRepository.existsByCardNumberAndAccountId(cardNumber, accountId);
    }

    @Override
    public List<Card> findAllCardsByAccountId(Long accountId) {
        return cardSqlRepository.findByAccountId(accountId);
    }

    @Override
    public void deleteCard(Long cardId) {
        cardSqlRepository.deleteById(cardId);
    }


}
