package com.manumb.digital_money_service.business.accounts.transactions.dto;

public record RequestCreateNewCardDepositTransaction(
        String cardNumber,
        Double amount
) {
}
