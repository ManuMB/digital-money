package com.manumb.digital_money_service.business.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RequestUpdateUser(
        @NotBlank(message = "full name cannot be blank")
        @Schema(description = "Nombre del usuario.",
                example = "ESTEBAN QUITO",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String fullName,
        @NotBlank(message = "dni cannot be blank")
        @Size(min = 11, max = 11, message = "dni must have 11 characters")
        @Schema(description = "Dni del titular de la tarjeta.",
                example = "12345678901",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String dni,
        @NotBlank(message = "email cannot be blank")
        @Email(message = "email must be a valid format")
        @Schema(description = "Email del usuario.",
                example = "example@email.com",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String email,
        @NotBlank(message = "phone number cannot be blank")
        @Schema(description = "Telefono del usuario.",
                example = "1112345678",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String phoneNumber
) {
}
