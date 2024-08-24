package com.manumb.digital_money_service.business.users.services;

import com.manumb.digital_money_service.business.exceptions.NotFoundException;
import com.manumb.digital_money_service.business.security.exception.UserNotFoundException;
import com.manumb.digital_money_service.business.users.User;
import com.manumb.digital_money_service.business.users.UserService;
import com.manumb.digital_money_service.persistence.UserSqlRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceHandler implements UserService, UserDetailsService {

    private final UserSqlRepository userSqlRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceHandler(UserSqlRepository userSqlRepository, PasswordEncoder passwordEncoder) {
        this.userSqlRepository = userSqlRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(User user) {
        var hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        userSqlRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        var userOptional = userSqlRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new UserNotFoundException("user " + email + " not found");
    }

    @Override
    public void deleteUser(Long id) {
        userSqlRepository.deleteById(id);
    }

    @Override
    public User findById(Long id) {
        return userSqlRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
    }

    @Override
    public void enableUser(String email) {
        userSqlRepository.enableAppUser(email);
    }

    @Override
    public void updatePassword(String firstPassword, String repeatedPassword, String email) {
        if (!firstPassword.equals(repeatedPassword)) {
            throw new RuntimeException("Las claves ingresadas son diferentes");
        }
        var hashedPassword = passwordEncoder.encode(firstPassword);
        userSqlRepository.changeUserPassword(hashedPassword, email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByEmail(username);
    }
}
