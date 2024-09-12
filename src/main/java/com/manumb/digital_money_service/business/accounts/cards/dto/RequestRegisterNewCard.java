package com.manumb.digital_money_service.business.accounts.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record RequestRegisterNewCard(
        @NotBlank(message = "card holder cannot be blank")
        @Schema(description = "Nombre del titular de la tarjeta.",
                example = "ESTEBAN QUITO",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String cardHolder,
        @NotBlank(message = "card number cannot be blank")
        @Size(min = 16, max = 16, message = "card number must have 16 characters")
        @Schema(description = "Numero de la tarjeta. Debe contener 16 caracteres.",
                example = "1112223334445556",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String cardNumber,
        @NotBlank(message = "cvv cannot be blank")
        @Size(min = 3, max = 4, message = "cvv must have between 3 and 4 characters")
        @Schema(description = "Codigo de seguridad de la tarjeta. Debe contener entre 3 y 4 caracteres.",
                example = "123",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String cvv,
        @NotNull(message = "expiration date cannot be null")
        @FutureOrPresent
        @Schema(description = "Fecha de vencimiento de la tarjeta. No debe ser una fecha y hora anterior a la actual.",
                example = "2030-01-01",
                requiredMode = Schema.RequiredMode.REQUIRED)
        LocalDate expirationDate
) {
}
