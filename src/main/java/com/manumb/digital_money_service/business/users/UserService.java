package com.manumb.digital_money_service.business.users;

import org.apache.coyote.BadRequestException;

import java.io.IOException;

public interface UserService {

    void saveUser(User user) throws IOException;

    void updateUser(Long id, String fullName, String dni, String email, String phoneNumber) throws BadRequestException;

    User findByEmail(String email);

    User findByDni(String dni);

    void deleteUser(Long id);

    User findById(Long id);

    void enableUser(String email);

    void updatePassword(String newPassword, String repeatNewPassword, String email);

}
