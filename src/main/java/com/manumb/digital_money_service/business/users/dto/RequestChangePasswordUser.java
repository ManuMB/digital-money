package com.manumb.digital_money_service.business.users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RequestChangePasswordUser(
        @NotBlank(message = "password cannot be blank")
        String firstPassword,
        @NotBlank(message = "password cannot be blank")
        String secondPassword,
        @NotBlank(message = "token cannot be blank")
        String token
) {
}
