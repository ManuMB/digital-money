package com.manumb.digital_money_service.business.accounts.cards.dto;

import java.time.LocalDate;

public record RequestRegisterNewCard(
        String cardHolder,
        String cardNumber,
        String cvv,
        LocalDate expirationDate,
        String accountId
) {
}
