package com.manumb.digital_money_service.business.accounts.transactions.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RequestCreateNewTransferTransaction(
        @NotBlank(message = "account indentifier cannot be blank")
        @Schema(description = "Alias o CVU de la cuenta a enviar la transferencia.",
                example = "CANTO.COCO.HOGAR",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String destinationAccountIdentifier,
        @NotNull(message = "ammount cannot be null")
        @Positive
        @Schema(description = "Cantidad de dinero a enviar en formato decimal.",
                example = "100.0",
                requiredMode = Schema.RequiredMode.REQUIRED)
        Double amount
) {
}
