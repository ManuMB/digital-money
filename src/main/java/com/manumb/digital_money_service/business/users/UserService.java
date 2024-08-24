package com.manumb.digital_money_service.business.users;

public interface UserService {

    void saveUser(User user);
    User findByEmail(String email);
    void deleteUser(Long id);
    User findById (Long id);
    void enableUser(String email);
    void updatePassword (String firstPassword, String repeatedPassword, String email);

}
