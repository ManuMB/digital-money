package com.manumb.digital_money_service.business.accounts.transactions.dto;

import java.time.LocalDateTime;

public record ResponseGetTransaction(
        Double amount,
        LocalDateTime transactionDate
) {
}
