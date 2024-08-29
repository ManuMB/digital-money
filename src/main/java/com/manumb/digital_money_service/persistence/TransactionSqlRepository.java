package com.manumb.digital_money_service.persistence;

import com.manumb.digital_money_service.business.accounts.transactions.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionSqlRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountIdOrderByTransactionDateDesc(Long accountId);
}
