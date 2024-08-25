package com.manumb.digital_money_service.business.users.dto;

public record ResponseRegisterNewUser(
        String fullName,
        String dni,
        String email,
        String phoneNumber,
        String cvu,
        String alias
) {
}
