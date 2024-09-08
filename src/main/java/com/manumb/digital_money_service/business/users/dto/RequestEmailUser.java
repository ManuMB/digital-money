package com.manumb.digital_money_service.business.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RequestEmailUser(
        @NotBlank(message = "email cannot be blank")
        @Email(message = "email must be a valid format")
        @Schema(description = "Email del usuario.",
                example = "example@email.com",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String email
) {
}
