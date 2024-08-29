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
    public List<ResponseGetTransaction> getLastTransactionsForAccount(Long accountId) {
        List<Transaction> transactions = transactionService.getLastTransactionsForAccount(accountId);
        return transactions.stream()
                .map(transaction -> new ResponseGetTransaction(
                        transaction.getAmount(),
                        transaction.getTransactionDate()
                ))
                .collect(Collectors.toList());
    }
}
