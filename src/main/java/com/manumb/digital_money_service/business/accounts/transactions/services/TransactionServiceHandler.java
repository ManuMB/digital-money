package com.manumb.digital_money_service.business.accounts.transactions.services;

import com.manumb.digital_money_service.business.accounts.transactions.Transaction;
import com.manumb.digital_money_service.business.accounts.transactions.TransactionService;
import com.manumb.digital_money_service.business.exceptions.NotFoundException;
import com.manumb.digital_money_service.persistence.TransactionSqlRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionServiceHandler implements TransactionService {
    private final TransactionSqlRepository transactionSqlRepository;

    @Override
    public void saveTransaction(Transaction transaction) {
        transactionSqlRepository.save(transaction);
    }

    @Override
    public List<Transaction> findLastFiveTransactionsForAccount(Long accountId) {
        return transactionSqlRepository.findLastFiveByAccountId(accountId);
    }

    @Override
    public List<Transaction> findAllTransactionsForAccount(Long accountId) {
        return transactionSqlRepository.findAllTransactionsByAccountId(accountId);
    }

    @Override
    public Transaction findTransactionById(Long id) {
        return transactionSqlRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Transaction with id " + id + " not found"));
    }
}
