package com.manumb.digital_money_service.orchestrator.accounts.transactions;

import com.manumb.digital_money_service.business.accounts.transactions.Transaction;
import com.manumb.digital_money_service.business.accounts.transactions.TransactionService;
import com.manumb.digital_money_service.business.accounts.transactions.dto.ResponseGetTransaction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionUseCaseHandler implements TransactionUseCaseOrchestrator{
    private final TransactionService transactionService;

    public TransactionUseCaseHandler(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public List<ResponseGetTransaction> getLastFiveTransactionsForAccount(Long accountId) {
        List<Transaction> transactions = transactionService.findLastFiveTransactionsForAccount(accountId);
        return transactions.stream()
                .map(transaction -> new ResponseGetTransaction(
                        transaction.getAmount(),
                        transaction.getTransactionDate()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<ResponseGetTransaction> getAllTransactionsForAccount(Long accountId) {
        List<Transaction> transactions = transactionService.findAllTransactionsForAccount(accountId);
        return transactions.stream()
                .map(transaction -> new ResponseGetTransaction(
                        transaction.getAmount(),
                        transaction.getTransactionDate()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseGetTransaction getTransactionById(Long id, Long accountId) {
        Transaction transaction = transactionService.findTransactionById(id, accountId);
        return new ResponseGetTransaction(
                transaction.getAmount(),
                transaction.getTransactionDate()
        );
    }
}
