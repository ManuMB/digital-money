package com.manumb.digital_money_service.business.accounts.transactions.dto;

public record RequestCreateNewTransferTransaction(
        String destinationAccountIdentifier,
        Double amount
) {
}
