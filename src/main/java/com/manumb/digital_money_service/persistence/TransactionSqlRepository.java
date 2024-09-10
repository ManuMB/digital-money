package com.manumb.digital_money_service.persistence;

import com.manumb.digital_money_service.business.accounts.transactions.Transaction;
import com.manumb.digital_money_service.business.accounts.transactions.TransactionDirection;
import com.manumb.digital_money_service.business.accounts.transactions.TransactionType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionSqlRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.fromAccount.id = :accountId OR t.toAccount.id = :accountId ORDER BY t.transactionDate DESC")
    List<Transaction> findAllTransactionsByAccountId(Long accountId);
    @Query("SELECT t FROM Transaction t WHERE t.fromAccount.id = :accountId OR t.toAccount.id = :accountId ORDER BY t.transactionDate DESC")
    List<Transaction> findTopFiveTransactionsByAccountId(Long accountId, Pageable pageable);
    default List<Transaction> findLastFiveByAccountId(Long accountId) {
        return findTopFiveTransactionsByAccountId(accountId, PageRequest.of(0, 5));
    }
    @Query("SELECT t FROM Transaction t " +
            "WHERE (t.fromAccount.id = :accountId OR t.toAccount.id = :accountId) " +
            "AND t.amount BETWEEN :minAmount AND :maxAmount " +
            "ORDER BY t.transactionDate DESC")
    List<Transaction> findByAccountIdAndAmount(
            Long accountId, Double minAmount, Double maxAmount);

    @Query("SELECT t FROM Transaction t " +
            "WHERE (t.fromAccount.id = :accountId OR t.toAccount.id = :accountId) " +
            "AND t.amount BETWEEN :minAmount AND :maxAmount " +
            "AND (:startDate IS NULL OR t.transactionDate >= :startDate) " +
            "AND (:endDate IS NULL OR t.transactionDate <= :endDate) " +
            "AND (:startDate IS NOT NULL AND :endDate IS NOT NULL AND t.transactionDate BETWEEN :startDate AND :endDate) " +
            "AND (:transactionType IS NULL OR t.transactionType = :transactionType) " +
            "ORDER BY t.transactionDate DESC")
    List<Transaction> findByAccountIdAndFilters(
            Long accountId, Double minAmount, Double maxAmount,
            LocalDateTime startDate, LocalDateTime endDate, TransactionType transactionType);



}
