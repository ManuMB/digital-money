package com.manumb.digital_money_service.persistence;

import com.manumb.digital_money_service.business.accounts.transactions.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionSqlRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountIdOrderByTransactionDateDesc(Long accountId);
    List<Transaction> findTop5ByAccountIdOrderByTransactionDateDesc(Long accountId);
    Optional<Transaction> findByIdAndAccountId(Long id, Long accountId);
}
