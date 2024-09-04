package com.manumb.digital_money_service.orchestrator.accounts.transactions;

import com.manumb.digital_money_service.business.accounts.transactions.dto.ResponseGetTransaction;

import java.util.List;

public interface TransactionUseCaseOrchestrator {
    List<ResponseGetTransaction> getLastFiveTransactionsForAccount(Long accountId);
    List<ResponseGetTransaction> getAllTransactionsForAccount(Long accountId);
    ResponseGetTransaction getTransactionById(Long id);
}
