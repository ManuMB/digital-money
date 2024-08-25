package com.manumb.digital_money_service.business.users.dto;

public record RequestUpdateUser(
        String fullName,
        String dni,
        String email,
        String phoneNumber
) {
}
