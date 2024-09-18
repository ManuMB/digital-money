package com.manumb.digital_money_service.business.accounts.transactions.exception;

public class DestinationAccountNotFoundException extends RuntimeException {
    public DestinationAccountNotFoundException(String message) {
        super(message);
    }
}
