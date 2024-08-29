package com.manumb.digital_money_service.orchestrator.accounts.transactions;

import com.manumb.digital_money_service.business.accounts.transactions.dto.ResponseGetTransaction;

import java.util.List;

public interface TransactionUseCaseOrchestrator {
    List<ResponseGetTransaction> getLastTransactionsForAccount(Long accountId);
}
