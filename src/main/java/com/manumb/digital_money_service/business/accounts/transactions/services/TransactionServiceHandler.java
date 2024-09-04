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
        return transactionSqlRepository.findLastFiveByAccountId(accountId);
    }

    @Override
    public List<Transaction> findAllTransactionsForAccount(Long accountId) {
        return transactionSqlRepository.findAllByAccountIdOrderByTransactionDateDesc(accountId);
    }

    @Override
    public Transaction findTransactionById(Long id) {
        return transactionSqlRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Card with id " + id + " not found"));
    }
}
