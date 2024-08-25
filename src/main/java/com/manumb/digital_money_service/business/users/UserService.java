package com.manumb.digital_money_service.business.users;

import java.io.IOException;

public interface UserService {

    void saveUser(User user) throws IOException;
    User findByEmail(String email);
    User findByDni(String dni);
    void deleteUser(Long id);
    User findById (Long id);
    void enableUser(String email);
    void updatePassword (String firstPassword, String repeatedPassword, String email);

}
