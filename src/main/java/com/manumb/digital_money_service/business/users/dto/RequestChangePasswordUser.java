package com.manumb.digital_money_service.business.users.dto;

import jakarta.validation.constraints.NotBlank;

public record RequestChangePasswordUser(
        @NotBlank(message = "password cannot be blank")
        String firstPassword,
        @NotBlank(message = "password cannot be blank")
        String secondPassword,
        @NotBlank(message = "token cannot be blank")
        String token
) {
}
