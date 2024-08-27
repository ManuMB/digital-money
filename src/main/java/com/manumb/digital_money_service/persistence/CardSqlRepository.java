package com.manumb.digital_money_service.persistence;

import com.manumb.digital_money_service.business.accounts.cards.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface CardSqlRepository extends JpaRepository<Card, Long> {
    boolean existsByCardNumberAndAccountIdNot(String cardNumber, Long accountId);
    boolean existsByCardHolderAndCardNumberAndCvvAndExpirationDateAndAccountId(String cardHolder, String cardNumber, String cvv, LocalDate expirationDate, Long accountId);
}
