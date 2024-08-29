package com.manumb.digital_money_service.business.accounts.transactions;

import java.util.List;

public interface TransactionService {
    List<Transaction> getLastTransactionsForAccount(Long accountId);
}
