package com.manumb.digital_money_service.business.accounts.transactions.exception;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(String message) {
        super(message);
    }
}
