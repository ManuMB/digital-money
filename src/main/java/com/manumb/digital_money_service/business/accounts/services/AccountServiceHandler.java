package com.manumb.digital_money_service.business.accounts.services;

import com.manumb.digital_money_service.business.accounts.Account;
import com.manumb.digital_money_service.business.accounts.AccountService;
import com.manumb.digital_money_service.business.accounts.transactions.exception.DestinationAccountNotFoundException;
import com.manumb.digital_money_service.business.exceptions.NotFoundException;
import com.manumb.digital_money_service.persistence.AccountSqlRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountServiceHandler implements AccountService {

    private final AccountSqlRepository accountSqlRepository;
    private final Random random = new SecureRandom();

    @Override
    public void saveAccount(Account account) {
        accountSqlRepository.save(account);
    }

    @Override
    public Account findById(Long id) {
        return accountSqlRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Account with id " + id + " not found"));
    }

    @Override
    public Account findByAliasOrCvu(String identifier) {
        return accountSqlRepository.findByAliasOrCvu(identifier)
                .orElseThrow(() -> new DestinationAccountNotFoundException("Destination account not found"));
    }

    @Override
    public void updateBalance(Long accountId, Double newBalance) {
        accountSqlRepository.updateBalance(accountId, newBalance);
    }

    @Override
    public void updateAlias(Long accountId, String newAlias) {
        Account existingAccount = accountSqlRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account with id " + accountId + " not found"));
        accountSqlRepository.updateAlias(accountId, newAlias);
    }

    @Override
    public String generateCVU() {
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

    @Override
    public String generateAlias() throws IOException {
        String alias;
        do {
            alias = generateRandomAlias();
        } while (accountSqlRepository.existsByAlias(alias));
        return alias;
    }

    private String generateRandomAlias() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("alias.txt");
        if (inputStream == null) {
            throw new FileNotFoundException("alias.txt not found in resources");
        }
        List<String> words = new BufferedReader(new InputStreamReader(inputStream))
                .lines()
                .collect(Collectors.toList());
        return words.get(random.nextInt(words.size())) + "." +
                words.get(random.nextInt(words.size())) + "." +
                words.get(random.nextInt(words.size()));
    }
}
