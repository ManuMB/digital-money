package com.manumb.digital_money_service.business.accounts.transactions.services;

import com.manumb.digital_money_service.business.accounts.transactions.Transaction;
import com.manumb.digital_money_service.business.accounts.transactions.TransactionService;
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
    public List<Transaction> getLastTransactionsForAccount(Long accountId) {
        return transactionSqlRepository.findByAccountIdOrderByTransactionDateDesc(accountId);
    }
}
