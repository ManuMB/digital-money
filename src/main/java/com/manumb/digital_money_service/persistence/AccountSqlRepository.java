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
    @Query("SELECT a FROM Account a WHERE a.alias = :identifier OR a.cvu = :identifier")
    Optional<Account> findByAliasOrCvu(String identifier);
    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.balance = :newBalance WHERE a.id = :accountId")
    void updateBalance(Long accountId, Double newBalance);

    boolean existsByCvu(String cvu);
    boolean existsByAlias(String alias);
}
