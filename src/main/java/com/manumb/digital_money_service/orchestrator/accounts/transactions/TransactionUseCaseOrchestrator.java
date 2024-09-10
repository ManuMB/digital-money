package com.manumb.digital_money_service.orchestrator.accounts.transactions;

import com.manumb.digital_money_service.business.accounts.transactions.TransactionDirection;
import com.manumb.digital_money_service.business.accounts.transactions.TransactionType;
import com.manumb.digital_money_service.business.accounts.transactions.dto.*;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionUseCaseOrchestrator {
    void createTransferTransaction(Long accountId, RequestCreateNewTransferTransaction request);

    void createCardDepositTransaction(Long accountId, RequestCreateNewCardDepositTransaction request);

    List<ResponseGetTransaction> getLastFiveTransactionsForAccount(Long accountId);

    List<ResponseGetTransaction> getAllTransactionsForAccount(Long accountId);

    ResponseGetTransaction getTransactionById(Long accountId, Long transactionId);

    List<ResponseGetTransaction> getTransactionsByAccountIdAndAmountRange(Long accountId, Double minAmount, Double maxAmount);}
