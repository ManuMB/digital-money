package com.manumb.digital_money_service.business.users.services;

import com.manumb.digital_money_service.business.accounts.Account;
import com.manumb.digital_money_service.business.security.exception.UserNotFoundException;
import com.manumb.digital_money_service.business.users.User;
import com.manumb.digital_money_service.business.users.UserService;
import com.manumb.digital_money_service.business.users.exception.UserExistsException;
import com.manumb.digital_money_service.persistence.AccountSqlRepository;
import com.manumb.digital_money_service.persistence.UserSqlRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class UserServiceHandler implements UserService, UserDetailsService {

    private final UserSqlRepository userSqlRepository;
    private final AccountSqlRepository accountSqlRepository;
    private final PasswordEncoder passwordEncoder;
    private final Random random = new SecureRandom();


    @Override
    public void saveUser(User user) throws IOException {
        if (userSqlRepository.existsByEmail(user.getEmail())) {
            throw new UserExistsException("Email already exists");
        }

        if (userSqlRepository.existsByDni(user.getDni())) {
            throw new UserExistsException("DNI already exists");
        }


        var hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        Account account = new Account();
        account.setCvu(generateUniqueCVU());
        account.setAlias(generateUniqueAlias());
        account.setBalance(0.0);
        user.setAccount(account);
        account.setUser(user);

        userSqlRepository.save(user);
    }

    @Override
    public void updateUser(Long id, String fullName, String dni, String email, String phoneNumber){
        // Check if the user exists by ID first
        User existingUser = userSqlRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));

        // Now check if the DNI or email already exist, but exclude the current user from the check
        if (userSqlRepository.existsByDni(dni) && !existingUser.getDni().equals(dni)) {
            throw new UserExistsException("DNI " + dni + " already in use");
        }

        if (userSqlRepository.existsByEmail(email) && !existingUser.getEmail().equals(email)) {
            throw new UserExistsException("Email " + email + " already in use");
        }

        // If everything is fine, update the user
        userSqlRepository.updateUser(id, fullName, dni, email, phoneNumber);
    }

    @Override
    public User findByEmail(String email) {
        return userSqlRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
    }

    @Override
    public User findByDni(String dni) {
        return userSqlRepository.findByDni(dni)
                .orElseThrow(() -> new UserNotFoundException("User with dni " + dni + " not found"));
    }

    @Override
    public void deleteUser(Long id) {
        userSqlRepository.deleteById(id);
    }

    @Override
    public User findById(Long id) {
        return userSqlRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public void enableUser(String email) {
        userSqlRepository.enableAppUser(email);
    }

    @Override
    public void updatePassword(String newPassword, String repeatNewPassword, String email) {
        if (!newPassword.equals(repeatNewPassword)) {
            throw new RuntimeException("Las claves ingresadas son diferentes");
        }
        var hashedPassword = passwordEncoder.encode(newPassword);
        userSqlRepository.changeUserPassword(hashedPassword, email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByEmail(username);
    }

    private String generateUniqueCVU() {
        String cvu;
        do {
            cvu = generateRandomCVU();
        } while (accountSqlRepository.existsByCvu(cvu));
        return cvu;
    }

    private String generateRandomCVU() {
        StringBuilder cvu = new StringBuilder(22);
        for (int i = 0; i < 22; i++) {
            cvu.append(random.nextInt(10));
        }
        return cvu.toString();
    }

    private String generateUniqueAlias() throws IOException {
        String alias;
        do {
            alias = generateRandomAlias();
        } while (accountSqlRepository.existsByAlias(alias));
        return alias;
    }

    private String generateRandomAlias() throws IOException {
        List<String> words = Files.readAllLines(Paths.get("src/main/java/com/manumb/digital_money_service/business/users/util/alias.txt"));
        return words.get(random.nextInt(words.size())) + "." +
                words.get(random.nextInt(words.size())) + "." +
                words.get(random.nextInt(words.size()));
    }
}
