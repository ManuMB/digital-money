package com.manumb.digital_money_service.business.users.dto;

public record ResponseRegisterNewUser(
        String fullName,
        String email,
        String dni,
        String phoneNumber,
        String cvu,
        String alias
) {
}
