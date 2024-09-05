package com.manumb.digital_money_service.persistence;

import com.manumb.digital_money_service.business.accounts.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AccountSqlRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUserId(Long userId);
    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.balance = :newBalance WHERE a.id = :accountId")
    void updateBalance(Long accountId, Double newBalance);
}
