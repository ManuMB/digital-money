package com.manumb.digital_money_service.business.users.dto;

public record RequestRegisterNewUser(
    String fullName,
    String email,
    String dni,
    String phoneNumber,
    String cvu,
    String alias,
    String password
) {
}
