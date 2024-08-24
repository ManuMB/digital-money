package com.manumb.digital_money_service.business.users.dto;

public record RequestChangePasswordUser(
        String firstPassword,
        String secondPassword,
        String token
) {
}
