package com.manumb.digital_money_service.orchestrator.accounts.transactions;

import com.manumb.digital_money_service.business.accounts.transactions.dto.RequestCreateNewCardDepositTransaction;
import com.manumb.digital_money_service.business.accounts.transactions.dto.RequestCreateNewTransferTransaction;
import com.manumb.digital_money_service.business.accounts.transactions.dto.ResponseGetTransaction;

import java.util.List;

public interface TransactionUseCaseOrchestrator {
    void createTransferTransaction(Long accountId, RequestCreateNewTransferTransaction request);

    void createCardDepositTransaction(Long accountId, RequestCreateNewCardDepositTransaction request);

    List<ResponseGetTransaction> getLastFiveTransactionsForAccount(Long accountId);

    List<ResponseGetTransaction> getAllTransactionsForAccount(Long accountId);

    ResponseGetTransaction getTransactionById(Long id);
}
