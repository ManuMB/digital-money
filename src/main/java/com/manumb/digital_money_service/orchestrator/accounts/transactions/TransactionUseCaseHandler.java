package com.manumb.digital_money_service.orchestrator.accounts.transactions;

import com.manumb.digital_money_service.business.accounts.Account;
import com.manumb.digital_money_service.business.accounts.AccountService;
import com.manumb.digital_money_service.business.accounts.transactions.Transaction;
import com.manumb.digital_money_service.business.accounts.transactions.TransactionService;
import com.manumb.digital_money_service.business.accounts.transactions.TransactionType;
import com.manumb.digital_money_service.business.accounts.transactions.dto.RequestCreateNewCardDepositTransaction;
import com.manumb.digital_money_service.business.accounts.transactions.dto.RequestCreateNewTransaction;
import com.manumb.digital_money_service.business.accounts.transactions.dto.ResponseGetTransaction;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionUseCaseHandler implements TransactionUseCaseOrchestrator{
    private final TransactionService transactionService;
    private final AccountService accountService;

    public TransactionUseCaseHandler(TransactionService transactionService, AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @Override
    public void createCardDepositTransaction(Long accountId, RequestCreateNewCardDepositTransaction request) {
        Account account = accountService.findById(accountId);

        Transaction transaction = new Transaction();
        transaction.setFromAccount(account);
        transaction.setToAccount(account);
        transaction.setAmount(request.amount());
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setTransactionType(TransactionType.CARD_DEPOSIT);

        accountService.updateBalance(account.getId(), account.getBalance() + request.amount());
        transactionService.saveTransaction(transaction);
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
    public ResponseGetTransaction getTransactionById(Long id) {
        Transaction transaction = transactionService.findTransactionById(id);
        return new ResponseGetTransaction(
                transaction.getAmount(),
                transaction.getTransactionDate()
        );
    }
}
