package com.manumb.digital_money_service.business.users.dto;

import jakarta.validation.constraints.NotBlank;

public record RequestChangePasswordUser(
        @NotBlank(message = "password cannot be blank")
        String newPassword,
        @NotBlank(message = "password cannot be blank")
        String repeatNewPassword,
        @NotBlank(message = "token cannot be blank")
        String token
) {
}
