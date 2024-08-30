package com.manumb.digital_money_service.business.accounts.transactions.services;

import com.manumb.digital_money_service.business.accounts.transactions.Transaction;
import com.manumb.digital_money_service.business.accounts.transactions.TransactionService;
import com.manumb.digital_money_service.business.exceptions.NotFoundException;
import com.manumb.digital_money_service.persistence.TransactionSqlRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceHandler implements TransactionService {
    private final TransactionSqlRepository transactionSqlRepository;

    public TransactionServiceHandler(TransactionSqlRepository transactionSqlRepository) {
        this.transactionSqlRepository = transactionSqlRepository;
    }

    @Override
    public List<Transaction> findLastFiveTransactionsForAccount(Long accountId) {
        return transactionSqlRepository.findTop5ByAccountIdOrderByTransactionDateDesc(accountId);
    }

    @Override
    public List<Transaction> findAllTransactionsForAccount(Long accountId) {
        return transactionSqlRepository.findByAccountIdOrderByTransactionDateDesc(accountId);
    }

    @Override
    public Transaction findTransactionById(Long id, Long accountId) {
        return transactionSqlRepository.findByIdAndAccountId(id, accountId)
            .orElseThrow(() -> new NotFoundException("Card with id " + id + " for account id " + accountId + " not found"));
    }
}
