package com.manumb.digital_money_service.business.accounts.cards.dto;

import java.time.LocalDate;

public record ResponseRegisterNewCard(
        String cardHolder,
        String cardNumber,
        String cvv,
        LocalDate expirationDate
) {
}
