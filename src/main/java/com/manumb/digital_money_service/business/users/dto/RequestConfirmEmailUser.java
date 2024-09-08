package com.manumb.digital_money_service.business.users.dto;

import jakarta.validation.constraints.NotBlank;

public record RequestConfirmEmailUser(
        @NotBlank(message = "token cannot be blank")
        String token
) {
}
