package com.manumb.digital_money_service.orchestrator.accounts.transactions;

import com.manumb.digital_money_service.business.accounts.Account;
import com.manumb.digital_money_service.business.accounts.AccountService;
import com.manumb.digital_money_service.business.accounts.cards.CardService;
import com.manumb.digital_money_service.business.accounts.cards.exception.CardNotFoundException;
import com.manumb.digital_money_service.business.accounts.transactions.Transaction;
import com.manumb.digital_money_service.business.accounts.transactions.TransactionService;
import com.manumb.digital_money_service.business.accounts.transactions.TransactionType;
import com.manumb.digital_money_service.business.accounts.transactions.dto.RequestCreateNewCardDepositTransaction;
import com.manumb.digital_money_service.business.accounts.transactions.dto.RequestCreateNewTransferTransaction;
import com.manumb.digital_money_service.business.accounts.transactions.dto.ResponseGetTransaction;
import com.manumb.digital_money_service.business.accounts.transactions.exception.InsufficientBalanceException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionUseCaseHandler implements TransactionUseCaseOrchestrator{
    private final TransactionService transactionService;
    private final AccountService accountService;
    private final CardService cardService;

    public TransactionUseCaseHandler(TransactionService transactionService, AccountService accountService, CardService cardService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.cardService = cardService;
    }


    @Override
    public void createTransferTransaction(Long accountId, RequestCreateNewTransferTransaction request) {
        Account senderAccount = accountService.findById(accountId);
        Account receiverAccount = accountService.findByAliasOrCvu(request.destinationAccountIdentifier());

        if (senderAccount.getBalance() < request.amount()) {
            throw new InsufficientBalanceException("Insufficient account balance for transfer");
        }

        Transaction transaction = new Transaction();
        transaction.setFromAccount(senderAccount);
        transaction.setToAccount(receiverAccount);
        transaction.setAmount(request.amount());
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setTransactionType(TransactionType.TRANSFER);

        accountService.updateBalance(senderAccount.getId(), (senderAccount.getBalance() - request.amount()));
        accountService.updateBalance(receiverAccount.getId(), (receiverAccount.getBalance() + request.amount()));

        transactionService.saveTransaction(transaction);
    }

    @Override
    public void createCardDepositTransaction(Long accountId, RequestCreateNewCardDepositTransaction request) {
        Account account = accountService.findById(accountId);
        if(!cardService.isFoundByCardNumberAndAccountId(request.cardNumber(), accountId)){
            throw new CardNotFoundException("Card with number: " + request.cardNumber() + " for user with id: " + accountId + " not found.");
        }

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
