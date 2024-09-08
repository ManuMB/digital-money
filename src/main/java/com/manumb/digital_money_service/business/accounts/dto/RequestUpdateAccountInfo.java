package com.manumb.digital_money_service.business.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record RequestUpdateAccountInfo(
        @NotBlank(message = "account alias cannot be blank")
        @Schema(description = "Alias de la cuenta, deben ser tres palabras separadas por un punto.",
                example = "CANTO.COCO.HOGAR",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String alias
) {
}
