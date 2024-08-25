package com.manumb.digital_money_service.persistence;

import com.manumb.digital_money_service.business.accounts.cards.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardSqlRepository extends JpaRepository<Card, Long> {
}
