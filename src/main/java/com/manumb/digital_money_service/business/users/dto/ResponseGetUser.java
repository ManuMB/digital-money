package com.manumb.digital_money_service.business.users.dto;

public record ResponseGetUser(
        String fullName,
        String dni,
        String email,
        String phoneNumber
) {
}
