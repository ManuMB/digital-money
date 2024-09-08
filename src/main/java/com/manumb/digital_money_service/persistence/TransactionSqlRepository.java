package com.manumb.digital_money_service.persistence;

import com.manumb.digital_money_service.business.accounts.transactions.Transaction;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionSqlRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.fromAccount.id = :accountId OR t.toAccount.id = :accountId ORDER BY t.transactionDate DESC")
    List<Transaction> findAllTransactionsByAccountId(Long accountId);
    @Query("SELECT t FROM Transaction t WHERE t.fromAccount.id = :accountId OR t.toAccount.id = :accountId ORDER BY t.transactionDate DESC")
    List<Transaction> findTopFiveTransactionsByAccountId(Long accountId, Pageable pageable);
    default List<Transaction> findLastFiveByAccountId(Long accountId) {
        return findTopFiveTransactionsByAccountId(accountId, PageRequest.of(0, 5));
    }
}
