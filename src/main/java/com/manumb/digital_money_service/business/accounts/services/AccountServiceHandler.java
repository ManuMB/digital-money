package com.manumb.digital_money_service.business.accounts.services;

import com.manumb.digital_money_service.business.accounts.Account;
import com.manumb.digital_money_service.business.accounts.AccountService;
import com.manumb.digital_money_service.business.exceptions.NotFoundException;
import com.manumb.digital_money_service.persistence.AccountSqlRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountServiceHandler implements AccountService {
    private final AccountSqlRepository accountSqlRepository;

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
                .orElseThrow(() -> new NotFoundException("Account not found"));
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
}
