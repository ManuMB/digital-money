package com.manumb.digital_money_service.persistence;

import com.manumb.digital_money_service.business.accounts.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountSqlRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUserId(Long userId);
}
