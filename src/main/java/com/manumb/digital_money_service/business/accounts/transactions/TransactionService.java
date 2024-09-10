package com.manumb.digital_money_service.business.accounts.transactions;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {
    void saveTransaction(Transaction transaction);

    List<Transaction> findLastFiveTransactionsForAccount(Long accountId);

    List<Transaction> findAllTransactionsForAccount(Long accountId);

    Transaction findTransactionById(Long id);

    List<Transaction> findTransactionsByAccountIdAndAmountRange(Long accountId, Double minAmount, Double maxAmount);
}
