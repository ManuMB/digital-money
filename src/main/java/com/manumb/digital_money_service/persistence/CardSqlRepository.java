package com.manumb.digital_money_service.persistence;

import com.manumb.digital_money_service.business.accounts.cards.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CardSqlRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByIdAndAccountId(Long id, Long accountId);
    List<Card> findByAccountId(Long accountId);
    void deleteById(Long id);
    boolean existsByCardNumberAndAccountId(String cardNumber, Long accountId);
    boolean existsByCardNumberAndAccountIdNot(String cardNumber, Long accountId);
    boolean existsByCardHolderAndCardNumberAndCvvAndExpirationDateAndAccountId(String cardHolder, String cardNumber, String cvv, LocalDate expirationDate, Long accountId);
}
