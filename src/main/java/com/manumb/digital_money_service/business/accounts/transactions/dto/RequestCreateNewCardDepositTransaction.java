package com.manumb.digital_money_service.business.accounts.transactions.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record RequestCreateNewCardDepositTransaction(
        @NotBlank(message = "card number cannot be blank")
        @Size(min = 16, max = 16, message = "card number must have 16 characters")
        @Schema(description = "Numero de la tarjeta. Debe contener 16 caracteres.",
                example = "111222333444555",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String cardNumber,
        @NotNull(message = "amount cannot be null")
        @Positive
        @Schema(description = "Cantidad de dinero a depositar en formato decimal.",
                example = "100.0",
                requiredMode = Schema.RequiredMode.REQUIRED)
        Double amount
) {
}
