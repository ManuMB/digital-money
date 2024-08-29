package com.manumb.digital_money_service.business.accounts.transactions.dto;

public record RequestCreateNewTransaction(
        Long accountId,
        Double amount
) {
}
